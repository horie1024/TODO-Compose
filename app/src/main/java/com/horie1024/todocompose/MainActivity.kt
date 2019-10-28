package com.horie1024.todocompose

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.Model
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.setContent
import androidx.ui.foundation.Clickable
import androidx.ui.layout.*
import androidx.ui.material.Checkbox
import androidx.ui.material.FloatingActionButton
import androidx.ui.material.MaterialTheme
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextDecoration
import androidx.ui.tooling.preview.Preview

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var tasks = listOf(
            TaskState(text = "task1", isChecked = false),
            TaskState(text = "task2", isChecked = true),
            TaskState(text = "task3", isChecked = false),
            TaskState(text = "task4", isChecked = false)
        )

        setContent {
            App(AppState(taskList = tasks)) {
                val list = tasks.toMutableList().add(TaskState("new task"))
                tasks = listOf(
                    TaskState(text = "task1", isChecked = false),
                    TaskState(text = "task2", isChecked = true),
                    TaskState(text = "task3", isChecked = false),
                    TaskState(text = "task4", isChecked = false),
                    TaskState("new task")
                )
                Log.i("test", tasks.size.toString())
            }
        }
    }
}

@Composable
fun App(appState: AppState = AppState(), onSelected: () -> Unit) {
    MaterialTheme {
        Column(modifier = ExpandedHeight) {
            Column(
                modifier = Flexible(1f)
            ) {
                HeightSpacer(height = 8.dp)
                TaskList(appState.taskList)
            }
            FloatingActionButton(
                onClick = {
                    onSelected()
                },
                children = {
                    //VectorAssetBuilder()
                }
            )

        }
    }
}

@Composable
fun TaskList(taskList: List<TaskState>) {
    for (task in taskList) {
        Task(state = task)
    }
}

@Composable
fun Task(state: TaskState) {
    Row(
        modifier = Spacing(4.dp)
    ) {
        Clickable(
            onClick = {
                state.isChecked = !state.isChecked
            }
        ) {
            TaskCheckbox(state)
            WidthSpacer(width = 4.dp)
            TaskText(state)
        }
    }
}

@Composable
fun TaskCheckbox(state: TaskState) {
    Checkbox(
        checked = state.isChecked,
        onCheckedChange = {
            state.isChecked = !state.isChecked
        }
    )
}

@Composable
fun TaskText(state: TaskState) {
    Text(
        text = state.text,
        style = TextStyle(
            decoration = if (state.isChecked) {
                TextDecoration.LineThrough
            } else {
                TextDecoration.None
            }
        )
    )
}

class AppState(val taskList: List<TaskState> = mutableListOf())

@Model
class TaskList(var tasks: MutableList<TaskState> = mutableListOf())

@Model
class TaskState(var text: String = "test", var isChecked: Boolean = false)

@Preview("TodoText preview")
@Composable
fun TodoTextPreview() {
    MaterialTheme {
        TaskText(state = TaskState())
    }
}