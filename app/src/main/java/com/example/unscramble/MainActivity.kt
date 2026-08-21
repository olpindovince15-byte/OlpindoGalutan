@file:Suppress("unused", "ObjectPropertyName", "SpellCheckingInspection")

package com.example.unscramble


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.unscramble.ui.theme.UnscrambleTheme
import kotlinx.coroutines.flow.MutableStateFlow



//Vincent Olpindo & Aaron Earl Galutan

@Suppress("unused")
const val MAX_NO_OF_WORDS = 10
@Suppress("unused")

const val SCORE_INCREASE = 20

val allWords: Set<String> = setOf(
    "animal",
    "auto",
    "anecdote",
    "alphabet",
    "all",
    "awesome",
    "arise",
    "balloon",
    "basket",
    "bench",
    "zoology",
    "zone",
    "zeal"
)

// Example usage that would remove the "unused" warning for allWords
@Suppress("unused")
fun getRandomWord(): String {
    return allWords.random()
}

@Composable
fun GameStatus(
    score: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.score, score),
            style = typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun GameLayout(
    modifier: Modifier = Modifier
) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(mediumPadding)
        ) {

            Text(
                modifier = Modifier
                    .clip(shapes.medium)
                    .background(colorScheme.surfaceTint)
                    .padding(
                        horizontal = 10.dp,
                        vertical = 4.dp
                    )
                    .align(Alignment.End),
                text = stringResource(R.string.word_count, 0),
                style = typography.titleMedium,
                color = colorScheme.onPrimary
            )

            Text(
                text = "scramble",
                style = typography.displayMedium
            )

            Text(
                text = stringResource(R.string.instructions),
                textAlign = TextAlign.Center,
                style = typography.titleMedium
            )

            OutlinedTextField(
                value = "",
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier.fillMaxWidth(),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface
                ),

                onValueChange = { },

                label = {
                    Text(
                        stringResource(R.string.enter_your_word)
                    )
                },

                isError = false,

                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),

                keyboardActions = KeyboardActions(
                    onDone = { }
                )
            )
        }
    }
}


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            UnscrambleTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    GameScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GameScreen(modifier: Modifier = Modifier) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(mediumPadding
            ),
        verticalArrangement = Arrangement.Center,

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.app_name),
            style = typography.titleLarge
        )

        GameLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(mediumPadding)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(mediumPadding),

            verticalArrangement = Arrangement.spacedBy(
                mediumPadding
            ),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { }
            ) {
                Text(
                    text = stringResource(R.string.submit),
                    fontSize = 16.sp
                )
            }

            OutlinedButton(
                onClick = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.skip),
                    fontSize = 16.sp
                )
            }
        }

        GameStatus(
            score = 0,
            modifier = Modifier.padding(20.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    UnscrambleTheme {
        GameScreen()
    }
}

@Composable
private fun FinalScoreDialog(score: Int, onPlayAgain: () -> Unit, modifier: Modifier = Modifier) {
    val activity = LocalActivity.current

    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onDismissRequest.
        },
        title = { Text(text = stringResource(R.string.congratulations)) },
        text = { Text(text = stringResource(R.string.you_scored, score)) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity?.finish()
                }
            ) {
                Text(text = stringResource(R.string.exit))
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = stringResource(R.string.play_again))
            }
        }
    )
}


class GameViewModel : ViewModel() {
    data class GameUiState(
        val currentScrambledWord: String = ""
    )
       private var _count = 0

    val count: Int
        get() = _count
}
// Game UI state
private val _uiState = MutableStateFlow(GameViewModel.GameUiState())









