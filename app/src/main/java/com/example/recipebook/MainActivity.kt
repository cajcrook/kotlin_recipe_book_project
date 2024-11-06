package com.example.recipebook

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipebook.ui.theme.RecipeBookTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeBookTheme {
                App()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun App() {
        val navController = rememberNavController()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Recipe Book",
                            fontSize = 32.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    },
                    navigationIcon = {
                        Text(
                            text = "🍕",
                            fontSize = 24.sp,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable(onClick = {
                                    navController.navigate("welcome_page")
                                })
                        )
                    },
                    modifier = Modifier.fillMaxWidth()

                )
            }
        ) { paddingValues ->
            NavHost(navController = navController, startDestination = "welcome_page") {

                composable(route = "welcome_page") {
                    WelcomePage(
                        onRecipeButtonClick = { navController.navigate("category_page") }
                    )
                }

                composable(route = "category_page") {
                    Categories(
                        onCategoryButtonClick = { categoryName ->
                            navController.navigate(categoryName)
                        }
                    )
                }

                composable(route = "italian") {
                    ItalianRecipePage(
                        recipes = listOf(
                            Recipe(R.string.Pizza_Recipe, R.drawable.pizza),
                            Recipe(R.string.Italian_Recipe_2, R.drawable.pizza),
                            Recipe(R.string.Italian_Recipe_3, R.drawable.pizza),
                            Recipe(R.string.Italian_Recipe_3, R.drawable.pizza)
                        ),
                        italianOnNextScreen = { navController.navigate("italian_details") }
                    )
                }

                composable(route = "japanese") {
                    JapaneseRecipePage(
                        recipes = listOf(
                            Recipe(R.string.Japanese_Recipe_1, R.drawable.sushi),
                            Recipe(R.string.Japanese_Recipe_2, R.drawable.sushi),
                            Recipe(R.string.Japanese_Recipe_3, R.drawable.sushi),
                            Recipe(R.string.Japanese_Recipe_4, R.drawable.sushi),
                        ),
                        japaneseOnNextScreen = { navController.navigate("japanese_details") }
                    )
                }

                composable(route = "italian_details") {
                    ItalianRecipeDetailsScreen()
                }
                composable(route = "japanese_details") {
                    JapaneseRecipeDetailsScreen()
                }
            }
        }
    }

    data class Recipe(
        val name: Int,
        val image: Int
    )

    data class Category(
        val category: Int
    )

    @Composable
    fun WelcomePage(onRecipeButtonClick: () -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome!",
                    fontSize = 32.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onRecipeButtonClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                ) {
                    Text(text = "Click here for recipes")
                }
            }
        }
    }

    @Composable
    fun Categories(
        modifier: Modifier = Modifier,
        categories: List<Category> = listOf(
            Category(R.string.Category_Italian),
            Category(R.string.Category_Japanese),
        ),
        onCategoryButtonClick: (String) -> Unit
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Text(
                text = "What's your favourite cuisine?!",
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 120.dp)
                    .padding(bottom = 40.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(categories) { category ->
                    val categoryName = when (category.category) {
                        R.string.Category_Italian -> "italian"
                        R.string.Category_Japanese -> "japanese"
                        else -> "Unknown" // Add additional mappings as needed
                    }
                    CategoryOptions(category) {
                        onCategoryButtonClick(categoryName)
                    }
                }
            }
        }
    }

    @Composable
    fun CategoryOptions(category: Category, onCategoryButtonClick: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = onCategoryButtonClick) {
                Text(
                    text = stringResource(id = category.category),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    @Composable
    fun ItalianRecipePage(
        recipes: List<Recipe>,  // Keep this as a parameter
        modifier: Modifier = Modifier,
        italianOnNextScreen: () -> Unit
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Text(
                text = "Italian Recipe List!!!",
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(recipes) { recipe ->
                    ItalianRecipeItem(recipe, italianOnNextScreen)
                }
            }
        }
    }


    @Composable
    fun ItalianRecipeItem(recipe: Recipe, italianOnNextScreen: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(recipe.image),
                contentDescription = "${recipe.name} Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Button(onClick = {
                italianOnNextScreen()
            }) {
                Text(
                    text = stringResource(id = recipe.name),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }

    @Composable
    fun JapaneseRecipePage(
        recipes: List<Recipe>,
        modifier: Modifier = Modifier,
        japaneseOnNextScreen: () -> Unit
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Text(
                text = "Japanese Recipe List!!!",
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(recipes) { recipe ->
                    JapaneseRecipeItem(recipe, japaneseOnNextScreen)
                }
            }
        }
    }

    @Composable
    fun JapaneseRecipeItem(recipe: Recipe, japaneseOnNextScreen: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(recipe.image),
                contentDescription = "${recipe.name} Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Button(onClick = {
                japaneseOnNextScreen()
            }) {
                Text(
                    text = stringResource(id = recipe.name),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }

    @Composable
    fun ItalianRecipeDetailsScreen() {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            text = "ItalianRecipeDetailsScreen",
        )
    }

    @Composable
    fun JapaneseRecipeDetailsScreen() {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            text = "JapaneseRecipeDetailsScreen",
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun RecipePreview() {
        RecipeBookTheme {
            App()
        }
    }
}
