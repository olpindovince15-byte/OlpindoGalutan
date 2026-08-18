package com.example.unscramble


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp



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


