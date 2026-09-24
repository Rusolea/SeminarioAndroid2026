package ar.edu.unicen.seminarioandroid2026.ui.popular

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unicen.seminarioandroid2026.R
import coil.compose.AsyncImage
import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.Movie
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun PopularMoviesScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: PopularMoviesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    // 🟢 Colectamos los ítems paginados
    val pagedMovies: LazyPagingItems<Movie> = viewModel.popularMoviesPagingFlow.collectAsLazyPagingItems()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // CAMPO DE BUSQUEDA
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { newQuery ->
                    viewModel.onSearchQueryChanged(newQuery)
                },
                label = { Text(text = stringResource(id = R.string.search_hint)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text(text = stringResource(id = R.string.search_hint)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onSearchQueryChanged("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = stringResource(id = R.string.clear_search)
                            )
                        }
                    }
                },
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )

            Box(modifier = Modifier.fillMaxSize()) {
                // 🛠️ Decidimos qué mostrar según si hay una búsqueda activa o no
                if (searchQuery.isNotEmpty()) {
                    // 🔍 FLUJO A: Mostrar resultados de búsqueda tradicionales
                    when (val state = uiState) {
                        is PopularMoviesUiState.Loading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                        is PopularMoviesUiState.Failure -> {
                            ErrorMoviesView(
                                message = stringResource(id = state.messageResId),
                                onRetry = { viewModel.fetchPopularMovies() },
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        is PopularMoviesUiState.Success -> {
                            MovieGrid(movies = state.movies, onMovieClick = onMovieClick)
                        }
                        is PopularMoviesUiState.Empty -> {
                            Text(
                                text = stringResource(id = R.string.no_movies_found),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                } else {
                    // 🔄 FLUJO B: Mostrar lista paginada infinita (Paging 3)
                    when (val refreshState = pagedMovies.loadState.refresh) {
                        is LoadState.Loading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                        is LoadState.Error -> {
                            // Capturamos el error de Paging y asignamos el string correspondiente
                            val errorException = refreshState.error
                            val errorMessageId = when (errorException) {
                                is java.net.UnknownHostException,
                                is java.io.IOException -> R.string.error_no_internet
                                else -> R.string.error_server
                            }
                            ErrorMoviesView(
                                message = stringResource(id = errorMessageId),
                                onRetry = { pagedMovies.retry() }, // 🟢 Reintenta la carga de Paging
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        is LoadState.NotLoading -> {
                            if (pagedMovies.itemCount == 0) {
                                Text(
                                    text = stringResource(id = R.string.no_movies_found),
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            } else {
                                // Mostramos la nueva Grid adaptada a Paging
                                PagedMovieGrid(movies = pagedMovies, onMovieClick = onMovieClick)
                            }
                        }
                    }
                }
            }
        }
    }
}

// 🟢 NUEVO: Grid exclusivo para manejar ítems paginados
@Composable
private fun PagedMovieGrid(
    movies: LazyPagingItems<Movie>,
    onMovieClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 140.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        // Paging 3 requiere usar movies.itemCount e indexar manualmente
        items(movies.itemCount) { index ->
            movies[index]?.let { movie ->
                MovieItem(
                    movie = movie,
                    onClick = { onMovieClick(movie.id) }
                )
            }
        }

        // Mostrar un spinner al final de la lista mientras se descargan más páginas
        if (movies.loadState.append is LoadState.Loading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(32.dp))
                }
            }
        }
    }
}

@Composable
private fun MovieGrid(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 140.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(movies) { movie ->
            MovieItem(
                movie = movie,
                onClick = { onMovieClick(movie.id) }
            )
        }
    }
}

@Composable
private fun MovieItem(
    movie: Movie,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.7f)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        // 🛠️ Configuración explícita para soportar Modo Claro y Oscuro
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface // Cambia automáticamente entre SurfaceLight y SurfaceDark
        )
    ) {
        Column {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentScale = ContentScale.Crop
            )
            Text(
                text = movie.title,
                color = MaterialTheme.colorScheme.onSurface, // 🟢 Texto legible: OnSurfaceLight (negro) u OnSurfaceDark (blanco)
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}



@Composable
private fun ErrorMoviesView(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            // 🟢 Usamos el string resource para el botón "Reintentar"
            Text(text = stringResource(id = R.string.retry))
        }
    }
}