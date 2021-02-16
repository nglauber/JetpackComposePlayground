package br.com.nglauber.jetpackcomposeplayground.screens

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import kotlin.math.max

class Score(
    team: String,
    score: Int
) {
    var team by mutableStateOf(team)
    var score by mutableStateOf(score)

    companion object {
        fun createSaver(): Saver<Score, Bundle> = Saver(
            save = { value ->
                   bundleOf(
                       "team" to value.team,
                       "score" to value.score
                   )
            },
            restore = { bundle ->
                Score(
                    bundle.getString("team", ""),
                    bundle.getInt("score", 0),
                )
            }
        )
    }
}

@Composable
fun ScoreScreen() {
    val home by rememberSaveable(stateSaver = Score.createSaver()) { mutableStateOf(
        Score(
            "Corinthians",
            0
        )
    ) }
    val visitor by rememberSaveable(stateSaver = Score.createSaver()) { mutableStateOf(
        Score(
            "Chelsea",
            0
        )
    ) }
    ScoreScreen(
        homeScore = home,
        visitorScore = visitor
    )
}

@Composable
fun ScoreScreen(homeScore: Score, visitorScore: Score) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Score") }) },
        bodyContent = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    TeamScore(score = homeScore)
                    Text(
                        text = "x",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        style = MaterialTheme.typography.h6
                    )
                    TeamScore(score = visitorScore)
                }
                OutlinedButton(
                    modifier = Modifier.padding(top = 16.dp),
                    content = { Text("Reset") },
                    onClick = {
                        homeScore.score = 0
                        visitorScore.score = 0
                    }
                )
            }
        }
    )
}

@Composable
fun TeamScore(score: Score) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = score.team, style = MaterialTheme.typography.h6)
        Button(
            content = { Text("+") },
            modifier = Modifier.testTag("TeamScore_Inc"),
            onClick = { score.score += 1 }
        )
        Text(text = score.score.toString(), style = MaterialTheme.typography.h5)
        Button(
            content = { Text("-") },
            modifier = Modifier.testTag("TeamScore_Dec"),
            onClick = { score.score = max(score.score - 1, 0) }
        )
    }
}