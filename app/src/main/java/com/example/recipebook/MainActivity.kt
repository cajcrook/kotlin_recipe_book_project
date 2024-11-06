package com.example.recipebook

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.recipebook.ui.theme.RecipeBookTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeBookTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    App()
                }
            }
        }
    }
}
@Composable()
fun App() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome_page") {

        composable(route = "welcome_page") {
            WelcomePage(
                onRecipeButtonClick = {navController.navigate("recipes_list")
                }
            )
        }
        composable(route = "recipes_list") {
            RecipeListPage(
                recipes = listOf(),
                onNextScreen = {navController.navigate("recipe_details")
            }
        )
    }
        composable(route = "recipe_details") {
            RecipeDetailsScreen()
        }
    }
}

data class Recipe(
    val name: Int,
    val image: Int
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
fun RecipeListPage(recipes: List<Recipe>, modifier: Modifier = Modifier, onNextScreen: () -> Unit) {
    val recipes = listOf(
        Recipe(R.string.Pizza_Recipe, R.drawable.pizza),
        Recipe(R.string.RecipeName2, R.drawable.pizza),
        Recipe(R.string.RecipeName3, R.drawable.pizza),
        Recipe(R.string.RecipeName4, R.drawable.pizza),
        )

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Text(
            text = "Recipe List!!!",
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(recipes) { recipe ->
                RecipeItem(recipe, onNextScreen )
            }
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe, onNextScreen: () -> Unit) {
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
//                .padding(bottom = 8.dp)
        )
        Button(onClick = {
            onNextScreen()
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

@Composable()
fun RecipeDetailsScreen() {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp),
        text = "RecipeDetailsScreen",
    )
}


@Preview(showBackground = true)
@Composable
fun RecipePreview() {
    RecipeBookTheme {
        App()
    }
}