package com.ggperroni.challengemobiledev.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.FolderOpen
import androidx.compose.material.icons.rounded.Sensors
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import com.ggperroni.challengemobiledev.data.remote.TreeItem

@Composable
fun HomeScreen(
    username: String,
    onBackToLogin: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val treeItems by viewModel.treeState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Confirm") },
            text = { Text("Do you really want to go back to the login screen?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        onBackToLogin()
                    },
                    modifier = Modifier
                        .width(100.dp)
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF3B67)),
                    shape = RoundedCornerShape(25.dp),
                ) {
                    Text(
                        "Yes",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false },
                    modifier = Modifier
                        .width(100.dp)
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCECECE)),
                    shape = RoundedCornerShape(25.dp),
                ) {
                    Text(
                        "No",
                        color = Color(0xFFFF3B67),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFF416C))
            .padding(top = 30.dp)
    ) {
        Column(modifier = Modifier.padding(top = 16.dp)) {
            IconButton(
                onClick = { showDialog = true }
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.padding(12.dp))
            Text("Hello", color = Color.White, fontSize = 20.sp)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(username, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.White,
                    RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
        ) {
            TreeView(
                items = treeItems,
                viewModel = viewModel,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun TreeView(items: List<TreeItem>, modifier: Modifier = Modifier, viewModel: HomeViewModel) {
    val tree = buildTree(items)
    LazyColumn(modifier = modifier) {
        items(tree) { item ->
            TreeItemView(item, viewModel = viewModel)
        }
    }
}

fun buildTree(items: List<TreeItem>, parentId: Int? = null): List<TreeItem> {
    return items.filter { it.parent == parentId }.map { item ->
        item.copy(children = buildTree(items, item.id))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreeItemView(
    item: TreeItem,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()

    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { showBottomSheet = false },
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            EditItemBottomSheet(
                item = item,
                onDismiss = { showBottomSheet = false },
                onSave = { name ->
                    viewModel.updateTree(item.id, name)
                    showBottomSheet = false
                }
            )
        }
    }

    Column(modifier = modifier.padding(start = item.level * 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
                .padding(8.dp)
        ) {
            Icon(
                imageVector = when (item.level) {
                    3 -> Icons.Rounded.Sensors
                    else -> {
                        if (isExpanded) {
                            Icons.Rounded.FolderOpen
                        } else {
                            Icons.Rounded.Folder
                        }
                    }
                },
                contentDescription = "Item",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = item.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.padding(8.dp))

            if (item.children.isNotEmpty()) {
                if (item.level == 2) {
                    IconButton(
                        onClick = { showBottomSheet = true },
                        modifier = Modifier.size(18.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = "Edit",
                            tint = Color(0xFFFF3B67),
                        )
                    }
                }
            }
        }

        if (isExpanded) {
            item.children.forEach { child ->
                TreeItemView(child, viewModel, modifier)
            }
        }
    }
}

@Composable
fun EditItemBottomSheet(
    item: TreeItem,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var name by remember { mutableStateOf(item.name) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Edit equipment name",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onSave(name) },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF3B67)),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text(
                "Save",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onDismiss,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCECECE)),
            shape = RoundedCornerShape(25.dp),
        ) {
            Text(
                "Cancel",
                color = Color(0xFFFF3B67),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen("admin", {})
}