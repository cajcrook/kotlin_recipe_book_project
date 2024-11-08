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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .clickable(onClick = {
                                    navController.navigate("welcome_page")
                                })
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
            Box(modifier = Modifier.padding(paddingValues))
            NavHost(
                navController = navController,
                startDestination = "welcome_page"
            )
            {
                composable(
                    route = "welcome_page"
                ) {
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
                            Recipe(R.string.Pizza_Recipe, R.drawable.pizza, R.string.Italian_Description_1),
                            Recipe(R.string.Italian_Recipe_2, R.drawable.pizza, R.string.Italian_Description_2),
                            Recipe(R.string.Italian_Recipe_3, R.drawable.pizza, R.string.Italian_Description_3),
                            Recipe(R.string.Italian_Recipe_4, R.drawable.pizza, R.string.Italian_Description_4)
                        ),
                        italianOnNextScreen = { recipe ->
                            navController.navigate("italian_details/${recipe.name}/${recipe.instruction}")
                        }
                    )
                }

                composable(route = "japanese") {
                    JapaneseRecipePage(
                        recipes = listOf(
                            Recipe(R.string.Japanese_Recipe_1, R.drawable.sushi, R.string.Japanese_Description_1),
                            Recipe(R.string.Japanese_Recipe_2, R.drawable.sushi, R.string.Japanese_Description_2),
                            Recipe(R.string.Japanese_Recipe_3, R.drawable.sushi, R.string.Japanese_Description_3),
                            Recipe(R.string.Japanese_Recipe_4, R.drawable.sushi, R.string.Japanese_Description_4),
                        ),
                        japaneseOnNextScreen = { recipe ->
                            navController.navigate("japanese_details/${recipe.name}/${recipe.instruction}")
                        }
                    )
                }

                composable(
                    route = "italian_details/{recipeName}/{recipeDescription}",
                    arguments = listOf(
                        navArgument("recipeName") { type = NavType.IntType },
                        navArgument("recipeDescription") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val recipeNameId = backStackEntry.arguments?.getInt("recipeName") ?: R.string.default_name
                    val recipeDescriptionId = backStackEntry.arguments?.getInt("recipeDescription") ?: R.string.default_description

                    ItalianRecipeDetailsScreen(
                        recipeName = stringResource(id = recipeNameId),
                        recipeDescription = stringResource(id = recipeDescriptionId),
                        onBackClick = { navController.navigate("italian") }
                    )
                }


                composable(
                    route = "japanese_details/{recipeName}/{recipeDescription}",
                    arguments = listOf(
                        navArgument("recipeName") { type = NavType.IntType },
                        navArgument("recipeDescription") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val recipeNameId = backStackEntry.arguments?.getInt("recipeName") ?: R.string.default_name
                    val recipeDescriptionId = backStackEntry.arguments?.getInt("recipeDescription") ?: R.string.default_description

                    JapaneseRecipeDetailsScreen(
                        recipeName = stringResource(id = recipeNameId),
                        recipeDescription = stringResource(id = recipeDescriptionId),
                        onBackClick = { navController.navigate("japanese") }
                    )
                }
                }
            }
        }
    }

    data class Recipe(val name: Int, val image: Int, val instruction: Int)
    data class Category(val category: Int)

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
                fontSize = 28.sp,
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
                        else -> "Unknown"
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
        recipes: List<Recipe>,
        italianOnNextScreen: (Recipe) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Text(
                text = "Italian Recipe List!",
                fontSize = 28.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 80.dp)

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
    fun ItalianRecipeItem(
        recipe: Recipe,
        italianOnNextScreen: (Recipe) -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Image(
                painter = painterResource(recipe.image),
                contentDescription = "${recipe.name} Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Button(onClick = {
                italianOnNextScreen(recipe)
            }) {
                Text(
                    text = stringResource(id = recipe.name),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    @Composable
    fun ItalianRecipeDetailsScreen(
        recipeName: String,
        recipeDescription: String,
        onBackClick: () -> Unit
    ) {
        val isLiked = remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Text(
                text = recipeName,
                fontSize = 28.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 80.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = recipeDescription,
                fontSize = 16.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            IconButton(
                onClick = { isLiked.value = !isLiked.value },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    imageVector = if (isLiked.value) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                    contentDescription = "Like Button",
                    tint = if (isLiked.value) Color.Red else Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Text(text = "Back to Italian Recipes")
            }
        }
    }

    @Composable
    fun JapaneseRecipePage(
        recipes: List<Recipe>,
        japaneseOnNextScreen: (Recipe) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Text(
                text = "Japanese Recipe List!",
                color = Color.Black,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 80.dp)
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
    fun JapaneseRecipeItem(recipe: Recipe, japaneseOnNextScreen: (Recipe) -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Image(
                painter = painterResource(recipe.image),
                contentDescription = "${recipe.name} Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Button(onClick = {
                japaneseOnNextScreen(recipe)
            }) {
                Text(
                    text = stringResource(id = recipe.name),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    @Composable
    fun JapaneseRecipeDetailsScreen(
        recipeName: String,
        recipeDescription: String,
        onBackClick: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Text(
                text = recipeName,
                fontSize = 24.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 80.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = recipeDescription,
                fontSize = 16.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Text(text = "Back to Japanese Recipes")
            }
        }
    }

