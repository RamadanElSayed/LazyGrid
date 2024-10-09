package com.instant.lazygrid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.instant.lazygrid.ui.theme.LazyGridTheme
import kotlin.math.max

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyGridTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    LazyColumn(
                        modifier = Modifier
                            .systemBarsPadding()
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        item {
                            Text(
                                text = "Lazy Grids in Jetpack Compose",
                                style = MaterialTheme.typography.headlineLarge,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                        }

                        LazyGridsExamples()
                    }
                }
            }
        }
    }

    private fun LazyListScope.LazyGridsExamples() {
        item {
            SectionTitle("Adaptive Photo Gallery")
            AdaptivePhotoGallery()
        }
        item {

            SectionTitle("Fixed Product Catalog")
            FixedProductCatalog()
        }
        item {

            SectionTitle("Horizontal Movie Poster Carousel")
            MoviePosterCarousel()
        }
        item {
            SectionTitle("Horizontal Product Slider")
            ProductSlider()
        }

        item {
            SectionTitle("Staggered Blog Feed")
            BlogFeed()
        }
        item {
            SectionTitle("Staggered Image Gallery")
            StaggeredImageGallery()
        }
        item {
            SectionTitle("Dynamic Product Catalog")
            DynamicProductCatalog()
        }
        item {

            SectionTitle("Dynamic Image Gallery")
            DynamicImageGallery()
        }
        item {
            SectionTitle(title = "Vertical Grid with Horizontal Sections")
            VerticalGridWithHorizontalSections()
        }

        item {
            Text(
                text = "Dynamic Vertical Grid",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            DynamicVerticalGrid(productList = productList)
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

////////////////////////////////////////////////////
// Example 1: Adaptive Photo Gallery (LazyVerticalGrid)
////////////////////////////////////////////////////
@Composable
fun AdaptivePhotoGallery() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .height(400.dp) // Adjust height for better scroll experience
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        items(photoList, key = { it.id }) { photo ->
            PhotoItem(photo = photo)
        }
    }
}

@Composable
fun PhotoItem(photo: Photo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photo.url)
                    .crossfade(true)
                    .build(),
                contentDescription = photo.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f) // Ensure consistent aspect ratio
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = photo.description, style = MaterialTheme.typography.bodySmall)
        }
    }
}

////////////////////////////////////////////////////////
// Example 2: Fixed Product Catalog (LazyVerticalGrid)
////////////////////////////////////////////////////////
@Composable
fun FixedProductCatalog() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .height(400.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        items(productList, key = { it.id }) { product ->
            ProductItem(product = product)
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f) // Consistent aspect ratio
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = product.name, style = MaterialTheme.typography.titleSmall)
            Text(
                text = product.price,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Green
            )
        }
    }
}

////////////////////////////////////////////////////
// Example 3: Movie Poster Carousel (LazyHorizontalGrid)
////////////////////////////////////////////////////
@Composable
fun MoviePosterCarousel() {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .height(250.dp)  // Adjusted height for horizontal grid
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        items(movieList, key = { it.id }) { movie ->
            MoviePosterItem(movie = movie)
        }
    }
}

@Composable
fun MoviePosterItem(movie: Movie) {
    Card(
        modifier = Modifier
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.DarkGray)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.posterUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f) // Consistent aspect ratio
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
        }
    }
}

////////////////////////////////////////////////////
// Example 4: Product Slider (LazyHorizontalGrid)
////////////////////////////////////////////////////
@Composable
fun ProductSlider() {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .height(250.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        items(productList, key = { it.id }) { product ->
            ProductItem(product = product)
        }
    }
}

////////////////////////////////////////////////////////
// Example 5: Blog Feed with Staggered Layout (Staggered Grid)
////////////////////////////////////////////////////////
@Composable
fun BlogFeed() {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .height(400.dp)  // Adjusted height for better scrolling
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        items(blogPostList, key = { it.id }) { blogPost ->
            StaggeredBlogPostItem(blogPost = blogPost)
        }
    }
}

@Composable
fun StaggeredBlogPostItem(blogPost: BlogPost) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(randomHeight())  // Dynamic height for staggered effect
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.Gray)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(blogPost.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = blogPost.title,
                modifier = Modifier
                    .fillMaxWidth()
                    //  .aspectRatio(1f) // Consistent aspect ratio
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = blogPost.title, style = MaterialTheme.typography.titleSmall)
            Text(text = blogPost.previewText, style = MaterialTheme.typography.bodySmall)
        }
    }
}

////////////////////////////////////////////////////////
// Example 6: Staggered Pinterest-Style Image Gallery
////////////////////////////////////////////////////////
@Composable
fun StaggeredImageGallery() {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(3),  // 3 columns for the gallery
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .height(400.dp)  // Adjusted height for better scrolling
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        items(photoList, key = { it.id }) { photo ->
            StaggeredImageItem(photo = photo)
        }
    }
}

@Composable
fun StaggeredImageItem(photo: Photo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(randomHeight())  // Random height for staggered effect
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photo.url)
                    .crossfade(true)
                    .build(),
                contentDescription = photo.description,
                modifier = Modifier
                    .fillMaxWidth()
                    //  .aspectRatio(1f)  // Consistent aspect ratio
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = photo.description, style = MaterialTheme.typography.bodySmall)
        }
    }
}


////////////////////////////////////////////////////
// Example 1: Dynamic Product Catalog (LazyVerticalGrid)
////////////////////////////////////////////////////
@Composable
fun DynamicProductCatalog() {
    // Get screen configuration
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val horizontalPadding = 16.dp
    val itemSpacing = 8.dp
    val availableWidth = screenWidth - horizontalPadding
    val minCellWidth = 150.dp
    val columnCount = max(1, (availableWidth / (minCellWidth + itemSpacing)).toInt())

    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .height(500.dp) // Adjust height for a better scroll experience
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        items(productList, key = { it.id }) { product ->
            ProductItem(product = product)
        }
    }
}


////////////////////////////////////////////////////
// Example 2: Dynamic Image Gallery (LazyVerticalGrid)
////////////////////////////////////////////////////
@Composable
fun DynamicImageGallery() {
    // Get screen configuration
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val horizontalPadding = 16
    val itemSpacing = 0
    val availableWidth = screenWidth - horizontalPadding
    val minCellWidth = 120
    val columnCount = max(1, (availableWidth / (minCellWidth + itemSpacing)).toInt())

    // Calculate the number of rows based on the number of items and columns
    val rows = (productList.size + columnCount - 1) / columnCount

    // Calculate the height of the grid based on item height and vertical spacing
    val itemHeight = 150
    val gridHeight = (rows * itemHeight) + ((rows - 1) * itemSpacing)

    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .height(gridHeight.dp)// Adjust height for a better scroll experience
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        items(photoList, key = { it.id }) { photo ->
            PhotoItem(photo = photo)
        }
    }
}

////////////////////////////////////////////////////
// Example 1: Vertical Grid with Nested Horizontal Grids
////////////////////////////////////////////////////
@Composable
fun VerticalGridWithHorizontalSections() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),  // Two columns in vertical grid
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()  // Use fillMaxWidth to occupy full width
            .height(400.dp) // Dynamically wrap content height up to a max of 600.dp
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        items(productList, key = { it.id }) { product ->
            ProductItem(product = product)
        }

        // Adding a horizontal section in the vertical grid
        item(span = { GridItemSpan(maxLineSpan) }) {  // Span across all columns
            Text(
                text = "Featured Movies",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )
            HorizontalMoviePosterCarousel()
        }

        item(span = { GridItemSpan(maxLineSpan) }) {  // Another horizontal section
            Text(
                text = "Top Products",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )
            HorizontalProductSlider()
        }

    }
}

////////////////////////////////////////////////////
// Example 2: Horizontal Movie Poster Carousel
////////////////////////////////////////////////////
@Composable
fun HorizontalMoviePosterCarousel() {
    // Set minimum item height and spacing
    val itemHeight = 150
    val itemSpacing = 8

    // Calculate number of rows based on the list size and a fixed number of columns
    val rows = 2 // Fixed number of rows
    // Calculate grid height based on the number of rows and spacing
    val gridHeight = (rows * itemHeight) + ((rows - 1) * itemSpacing)

    LazyHorizontalGrid(
        rows = GridCells.Fixed(rows),  // Dynamic number of rows
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(itemSpacing.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(gridHeight.dp)  // Dynamically calculated height
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        items(movieList, key = { it.id }) { movie ->
            MoviePosterItem(movie = movie)
        }
    }
}


////////////////////////////////////////////////////
// Example 3: Horizontal Product Slider
////////////////////////////////////////////////////
@Composable
fun HorizontalProductSlider() {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(1),  // One row in horizontal grid for product slider
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .height(150.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        items(productList, key = { it.id }) { product ->
            ProductItem(product = product)
        }
    }
}


@Composable
fun DynamicVerticalGrid(productList: List<Product>) {
    // Screen width, dynamic calculation for columns and grid height
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val horizontalPadding = 16.dp
    val itemSpacing = 8
    val availableWidth = screenWidth - horizontalPadding * 2 // Account for padding on both sides
    val minCellWidth = 150.dp // Minimum width for each item
    val columns = max(1, (availableWidth / (minCellWidth + itemSpacing.dp)).toInt())

    // Calculate the number of rows based on the number of items and columns
    val rows = (productList.size + columns - 1) / columns

    // Calculate the height of the grid based on item height and vertical spacing
    val itemHeight = 150
    val gridHeight = (rows * itemHeight) + ((rows - 1) * itemSpacing)


    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(itemSpacing.dp),
        verticalArrangement = Arrangement.spacedBy(itemSpacing.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(gridHeight.dp)  // Dynamically calculated height
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        items(productList, key = { it.id }) { product ->
            ProductItem(product = product)
        }
    }
}

////////////////////////////////////////////////////////////
// Supporting Data for Each Example
////////////////////////////////////////////////////////////

val photoList = listOf(
    Photo(
        1,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ1jl_IhNcfipvMyNeo3nqLEWtYTi4V8EqmxgijwFXZd0_MPv1m95PZzB9-5K1IoLpARU0&usqp=CAU",
        "Photo 1"
    ),
    Photo(
        2,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ1jl_IhNcfipvMyNeo3nqLEWtYTi4V8EqmxgijwFXZd0_MPv1m95PZzB9-5K1IoLpARU0&usqp=CAU",
        "Photo 2"
    ),
    Photo(
        3,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ1jl_IhNcfipvMyNeo3nqLEWtYTi4V8EqmxgijwFXZd0_MPv1m95PZzB9-5K1IoLpARU0&usqp=CAU",
        "Photo 3"
    ),
    Photo(
        4,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ1jl_IhNcfipvMyNeo3nqLEWtYTi4V8EqmxgijwFXZd0_MPv1m95PZzB9-5K1IoLpARU0&usqp=CAU",
        "Photo 4"
    ),
    Photo(
        5,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ1jl_IhNcfipvMyNeo3nqLEWtYTi4V8EqmxgijwFXZd0_MPv1m95PZzB9-5K1IoLpARU0&usqp=CAU",
        "Photo 1"
    ),
    Photo(
        6,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ1jl_IhNcfipvMyNeo3nqLEWtYTi4V8EqmxgijwFXZd0_MPv1m95PZzB9-5K1IoLpARU0&usqp=CAU",
        "Photo 2"
    ),
    Photo(
        7,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ1jl_IhNcfipvMyNeo3nqLEWtYTi4V8EqmxgijwFXZd0_MPv1m95PZzB9-5K1IoLpARU0&usqp=CAU",
        "Photo 3"
    ),
    Photo(
        8,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ1jl_IhNcfipvMyNeo3nqLEWtYTi4V8EqmxgijwFXZd0_MPv1m95PZzB9-5K1IoLpARU0&usqp=CAU",
        "Photo 4"
    ),
)

val productList = listOf(
    Product(
        1,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQZcqQM3qF8MnPOP879NsRYvoCAKHeQphPK8XiKNZWoEGxa_hCCHr_vepThcSD0fDKnnIQ&usqp=CAU",
        "Product 1",
        "$10.00"
    ),
    Product(
        2,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQZcqQM3qF8MnPOP879NsRYvoCAKHeQphPK8XiKNZWoEGxa_hCCHr_vepThcSD0fDKnnIQ&usqp=CAU",
        "Product 2",
        "$15.00"
    ),
    Product(
        3,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQZcqQM3qF8MnPOP879NsRYvoCAKHeQphPK8XiKNZWoEGxa_hCCHr_vepThcSD0fDKnnIQ&usqp=CAU",
        "Product 3",
        "$20.00"
    ),
    Product(
        4,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQZcqQM3qF8MnPOP879NsRYvoCAKHeQphPK8XiKNZWoEGxa_hCCHr_vepThcSD0fDKnnIQ&usqp=CAU",
        "Product 4",
        "$25.00"
    ),
    Product(
        5,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQZcqQM3qF8MnPOP879NsRYvoCAKHeQphPK8XiKNZWoEGxa_hCCHr_vepThcSD0fDKnnIQ&usqp=CAU",
        "Product 1",
        "$10.00"
    ),
    Product(
        6,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQZcqQM3qF8MnPOP879NsRYvoCAKHeQphPK8XiKNZWoEGxa_hCCHr_vepThcSD0fDKnnIQ&usqp=CAU",
        "Product 2",
        "$15.00"
    ),
    Product(
        7,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQZcqQM3qF8MnPOP879NsRYvoCAKHeQphPK8XiKNZWoEGxa_hCCHr_vepThcSD0fDKnnIQ&usqp=CAU",
        "Product 3",
        "$20.00"
    ),
    Product(
        8,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQZcqQM3qF8MnPOP879NsRYvoCAKHeQphPK8XiKNZWoEGxa_hCCHr_vepThcSD0fDKnnIQ&usqp=CAU",
        "Product 4",
        "$25.00"
    ),
)

val movieList = listOf(
    Movie(
        1,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQjwLI8Pl_ucjthnPpdMO6g5XAT4E-_IFfZ1-8ODOA8zWvd_kzPz79ZHsZ4ucczgk698_I&usqp=CAU",
        "Movie 1"
    ),
    Movie(
        2,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQjwLI8Pl_ucjthnPpdMO6g5XAT4E-_IFfZ1-8ODOA8zWvd_kzPz79ZHsZ4ucczgk698_I&usqp=CAU",
        "Movie 2"
    ),
    Movie(
        3,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQjwLI8Pl_ucjthnPpdMO6g5XAT4E-_IFfZ1-8ODOA8zWvd_kzPz79ZHsZ4ucczgk698_I&usqp=CAU",
        "Movie 3"
    ),
    Movie(
        4,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQjwLI8Pl_ucjthnPpdMO6g5XAT4E-_IFfZ1-8ODOA8zWvd_kzPz79ZHsZ4ucczgk698_I&usqp=CAU",
        "Movie 4"
    ),
    Movie(
        5,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQjwLI8Pl_ucjthnPpdMO6g5XAT4E-_IFfZ1-8ODOA8zWvd_kzPz79ZHsZ4ucczgk698_I&usqp=CAU",
        "Movie 1"
    ),
    Movie(
        6,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQjwLI8Pl_ucjthnPpdMO6g5XAT4E-_IFfZ1-8ODOA8zWvd_kzPz79ZHsZ4ucczgk698_I&usqp=CAU",
        "Movie 2"
    ),
    Movie(
        7,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQjwLI8Pl_ucjthnPpdMO6g5XAT4E-_IFfZ1-8ODOA8zWvd_kzPz79ZHsZ4ucczgk698_I&usqp=CAU",
        "Movie 3"
    ),
    Movie(
        8,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQjwLI8Pl_ucjthnPpdMO6g5XAT4E-_IFfZ1-8ODOA8zWvd_kzPz79ZHsZ4ucczgk698_I&usqp=CAU",
        "Movie 4"
    ),
)

val blogPostList = listOf(
    BlogPost(
        1,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWkQxPTXACMvxo-Tar1W42PHp3V_EdTSFSxe1WbgA1EJEjbHHagWPd5lCcr_VqFs8uaj4&usqp=CAU",
        "Blog Post 1",
        "Preview text 1"
    ),
    BlogPost(
        2,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWkQxPTXACMvxo-Tar1W42PHp3V_EdTSFSxe1WbgA1EJEjbHHagWPd5lCcr_VqFs8uaj4&usqp=CAU",
        "Blog Post 2",
        "Preview text 2"
    ),
    BlogPost(
        3,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWkQxPTXACMvxo-Tar1W42PHp3V_EdTSFSxe1WbgA1EJEjbHHagWPd5lCcr_VqFs8uaj4&usqp=CAU",
        "Blog Post 3",
        "Preview text 3"
    ),
    BlogPost(
        4,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWkQxPTXACMvxo-Tar1W42PHp3V_EdTSFSxe1WbgA1EJEjbHHagWPd5lCcr_VqFs8uaj4&usqp=CAU",
        "Blog Post 4",
        "Preview text 4"
    ),
    BlogPost(
        5,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWkQxPTXACMvxo-Tar1W42PHp3V_EdTSFSxe1WbgA1EJEjbHHagWPd5lCcr_VqFs8uaj4&usqp=CAU",
        "Blog Post 1",
        "Preview text 1"
    ),
    BlogPost(
        6,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWkQxPTXACMvxo-Tar1W42PHp3V_EdTSFSxe1WbgA1EJEjbHHagWPd5lCcr_VqFs8uaj4&usqp=CAU",
        "Blog Post 2",
        "Preview text 2"
    ),
    BlogPost(
        7,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWkQxPTXACMvxo-Tar1W42PHp3V_EdTSFSxe1WbgA1EJEjbHHagWPd5lCcr_VqFs8uaj4&usqp=CAU",
        "Blog Post 3",
        "Preview text 3"
    ),
    BlogPost(
        8,
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWkQxPTXACMvxo-Tar1W42PHp3V_EdTSFSxe1WbgA1EJEjbHHagWPd5lCcr_VqFs8uaj4&usqp=CAU",
        "Blog Post 4",
        "Preview text 4"
    ),
)

fun randomHeight(): Dp {
    return (150..300).random().dp
}

////////////////////////////////////////////////////////////
// Data Classes
////////////////////////////////////////////////////////////
data class Photo(val id: Int, val url: String, val description: String)
data class Product(val id: Int, val imageUrl: String, val name: String, val price: String)
data class Movie(val id: Int, val posterUrl: String, val title: String)
data class BlogPost(val id: Int, val imageUrl: String, val title: String, val previewText: String)
