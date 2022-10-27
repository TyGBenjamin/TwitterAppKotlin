package com.example.twittertest.presentation.screens.dashboard

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.lib_data.domain.models.Post
import com.example.lib_data.util.Resource
import com.example.twittertest.R


@Composable
fun Dashboard(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel(),
) {
    val post by viewModel.posts.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painterResource(R.drawable.twitter_logo),
                modifier = Modifier.size(55.dp),
                contentDescription = "logo"
            )
            Button(
                onClick = {
                    navController.navigate("login_screen")
                }
            ) {
                Text(text = "Logout")
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        )

        {
            when (post) {
                is Resource.Error -> {}
                Resource.Loading -> {}
                is Resource.Success -> {
                    val successfulPost = (post as Resource.Success<List<Post>>).data
                    println("${successfulPost}")

                    Log.d("POST LOGGER", " POST RIGHT HERE ${successfulPost}")
                    items(successfulPost) { post ->
                        Post(post)
                    }
                }
            }
        }
        Button(
            onClick = {
                navController.navigate("add_post_screen")
                println(" Add post Button clicked")
            }, modifier = Modifier
                .wrapContentSize()
                .height(50.dp)
        ) {
            Text(text = " Add Post")
        }
    }

}


@Composable
fun Post(
    data: Post,
//    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))

    ) {
        Row(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max))
        {
            Image(
                painter = rememberAsyncImagePainter(
                    "https://image.shutterstock.com/image-vector/one-piece-character-luffy-nika-600w-2210725005.jpg"
                ),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Column() {


                Text(text = "@${data.username}")
                Text(
                    text = data.content,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
                Text(text = "comments", fontSize = 15.sp)


            }
            Text(text = "Time", textAlign = TextAlign.End)

        }
    }
}





