@file:Suppress("unused", "ObjectPropertyName", "SpellCheckingInspection")

package com.example.unscramble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.LocalActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.unscramble.ui.theme.UnscrambleTheme




// Vincent Olpindo & Aaron Earl Galutan

const val MAX_NO_OF_WORDS = 10

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


// Returns a random word from the word list
fun getRandomWord(): String {
    return allWords.random()
}


// ---------------------------------------------------------
// PHASE 1 - Game Status
// ---------------------------------------------------------

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


// ---------------------------------------------------------
// PHASE 1 + PHASE 3 - Game Layout
// ---------------------------------------------------------

@Composable
fun GameLayout(
    onUserGuessChanged: (String) -> Unit,
    userGuess: String,
    onKeyboardDone: () -> Unit,
    currentScrambledWord: String,
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

            // Word count
            Text(
                modifier = Modifier
                    .clip(shapes.medium)
                    .background(colorScheme.surfaceTint)
                    .padding(
                        horizontal = 10.dp,
                        vertical = 4.dp
                    )
                    .align(Alignment.End),

                text = stringResource(
                    R.string.word_count,
                    0
                ),

                style = typography.titleMedium,

                color = colorScheme.onPrimary
            )


            // PHASE 2
            // Display scrambled word
            Text(
                text = currentScrambledWord,
                style = typography.displayMedium
            )


            // Instructions
            Text(
                text = stringResource(R.string.instructions),
                textAlign = TextAlign.Center,
                style = typography.titleMedium
            )


            // PHASE 3
            // Accept user's answer
            OutlinedTextField(

                value = userGuess,

                singleLine = true,

                shape = shapes.large,

                modifier = Modifier.fillMaxWidth(),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface
                ),

                onValueChange = onUserGuessChanged,

                label = {
                    Text(
                        stringResource(
                            R.string.enter_your_word
                        )
                    )
                },

                // Phase 4 has not been implemented yet
                isError = false,

                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),

                keyboardActions = KeyboardActions(
                    onDone = {
                        onKeyboardDone()
                    }
                )
            )
        }
    }
}


// ---------------------------------------------------------
// MAIN ACTIVITY
// ---------------------------------------------------------

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


// ---------------------------------------------------------
// GAME SCREEN
// ---------------------------------------------------------

@Preview(showBackground = true)
@Composable
fun GameScreen(
    modifier: Modifier = Modifier
) {

    // PHASE 3
    // Store the user's answer locally.
    var userGuess by remember {
        mutableStateOf("")
    }


    // PHASE 2
    // Get a scrambled word.
    var currentScrambledWord by remember {
        mutableStateOf(
            shuffleCurrentWord(
                getRandomWord()
            )
        )
    }


    val mediumPadding =
        dimensionResource(R.dimen.padding_medium)


    Column(

        modifier = modifier
            .verticalScroll(
                rememberScrollState()
            )
            .padding(mediumPadding),

        verticalArrangement = Arrangement.Center,

        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        // App title
        Text(
            text = stringResource(R.string.app_name),
            style = typography.titleLarge
        )


        // Game area
        GameLayout(

            onUserGuessChanged = {
                userGuess = it
            },

            userGuess = userGuess,

            // Phase 4 not implemented yet
            onKeyboardDone = {
            },

            currentScrambledWord = currentScrambledWord,

            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(mediumPadding)
        )


        // Buttons
        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(mediumPadding),

            verticalArrangement = Arrangement.spacedBy(
                mediumPadding
            ),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            // Submit button
            Button(

                modifier = Modifier.fillMaxWidth(),

                // Phase 4 not implemented yet
                onClick = {
                }

            ) {

                Text(
                    text = stringResource(
                        R.string.submit
                    ),

                    fontSize = 16.sp
                )
            }


            // Skip button
            OutlinedButton(

                onClick = {
                    // Phase 6 not implemented yet
                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text(
                    text = stringResource(
                        R.string.skip
                    ),

                    fontSize = 16.sp
                )
            }
        }


        // Score
        GameStatus(
            score = 0,
            modifier = Modifier.padding(20.dp)
        )
    }
}


// ---------------------------------------------------------
// PHASE 2 - Shuffle Word
// ---------------------------------------------------------

fun shuffleCurrentWord(
    word: String
): String {

    val tempWord = word.toCharArray()

    tempWord.shuffle()

    // Make sure scrambled word isn't
    // identical to the original word.
    while (String(tempWord) == word) {
        tempWord.shuffle()
    }

    return String(tempWord)
}


// ---------------------------------------------------------
// PREVIEW
// ---------------------------------------------------------

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {

    UnscrambleTheme {
        GameScreen()
    }
}


// ---------------------------------------------------------
// FINAL SCORE DIALOG
// ---------------------------------------------------------
// This is UI provided for a later phase.
// It is not connected yet.

@Composable
private fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {

    val activity = LocalActivity.current

    AlertDialog(

        onDismissRequest = {
            // Dialog will be connected later
        },

        title = {
            Text(
                text = stringResource(
                    R.string.congratulations
                )
            )
        },

        text = {
            Text(
                text = stringResource(
                    R.string.you_scored,
                    score
                )
            )
        },

        modifier = modifier,

        dismissButton = {

            TextButton(
                onClick = {
                    activity?.finish()
                }
            ) {

                Text(
                    text = stringResource(
                        R.string.exit
                    )
                )
            }
        },

        confirmButton = {

            TextButton(
                onClick = onPlayAgain
            ) {

                Text(
                    text = stringResource(
                        R.string.play_again
                    )
                )
            }
        }
    )
}