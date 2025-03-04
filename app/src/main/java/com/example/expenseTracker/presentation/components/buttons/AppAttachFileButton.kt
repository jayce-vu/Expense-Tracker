package com.example.expenseTracker.presentation.components.buttons

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage

@SuppressLint("ContextCastToActivity")
@Composable
fun AttachFileButton(
    title: String = "Attach File",
    onFileSelected: (List<Uri?>) -> Unit
) {
    val selectedFiles = remember { mutableStateListOf<Pair<String, Uri>>() } // ✅ Store name & URI
    val activity = LocalContext.current as? Activity

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            activity?.let { nonNullActivity ->
                val name = getFileName(nonNullActivity, it)
                Log.d("FilePicker", "Selected file: $name")
                selectedFiles.add(name to it) // ✅ Add file name & URI
                onFileSelected.invoke(selectedFiles.map { item -> item.second })
            }
        }
    }

    Column {
        LazyColumn {
            items(selectedFiles.size) { index ->
                FileItem(selectedFiles[index].first, selectedFiles[index].second)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    filePickerLauncher.launch("*/*")
                }
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(title) },
                enabled = false,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    IconButton(onClick = { filePickerLauncher.launch("*/*") }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add file"
                        )
                    }
                },
                readOnly = true,
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledContainerColor = Color.White,
                    disabledTextColor = Color.Black,
                    disabledBorderColor = Color.Black,
                    disabledTrailingIconColor = Color.Black
                )
            )
        }
    }
}

@Composable
fun FileItem(fileName: String, uri: Uri) {
    val context = LocalContext.current
    val isImage = remember(uri) { checkIfImage(context, uri) } // ✅ Check if it's an image

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isImage) {
            GlideImage(
                imageModel = uri,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(
                        RoundedCornerShape(4.dp)
                    )
            )
        } else {
            Icon(
                imageVector = Icons.Default.Share, // ✅ Default file icon
                contentDescription = "File",
                modifier = Modifier.size(40.dp),
                tint = Color.Gray
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(fileName, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

private fun checkIfImage(context: Context, uri: Uri): Boolean {
    val contentResolver = context.contentResolver
    val type = contentResolver.getType(uri)
    return type?.startsWith("image/") == true
}

private fun getFileName(activity: Activity, uri: Uri): String {
    var name = "Unknown"
    activity.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (cursor.moveToFirst() && nameIndex >= 0) {
            name = cursor.getString(nameIndex)
        }
    }
    return name
}


@Preview
@Composable
fun PreviewAttachFileButton() {
    AttachFileButton(onFileSelected = {})
}
