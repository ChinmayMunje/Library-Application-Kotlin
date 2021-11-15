package com.gtappdevelopers.libraryapplication

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class HomeActivity : AppCompatActivity() {

    lateinit var booksRV:RecyclerView
    lateinit var loadingPB:ProgressBar
    lateinit var searchEdt: EditText
    lateinit var searchIV: ImageView
    lateinit var bookRVAdapter: BookRVAdapter
    lateinit var bookList: List<BookRVModal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        booksRV = findViewById(R.id.idRVBooks)
        loadingPB = findViewById(R.id.idPBLoading)
        searchEdt = findViewById(R.id.idEdtSearch)
        searchIV = findViewById(R.id.idIVSearch)

        getBooks("engineering")
        searchIV.setOnClickListener {
            val query:String = searchEdt.text.toString()
            if(query.isNotEmpty()){
                getBooks(query)
            }
        }


    }

    private fun getBooks(query:String) {

        val url = "https://www.googleapis.com/books/v1/volumes?q="+query
        val queue = Volley.newRequestQueue(this)
        bookList = ArrayList<BookRVModal>()

        loadingPB.visibility = View.VISIBLE
        val request = JsonObjectRequest(Request.Method.GET,url,null,{
            response->
            loadingPB.visibility = View.GONE
            try {
                val itemArray = response.getJSONArray("items")
                if(itemArray.length().equals(0)){
                    Toast.makeText(this,"No Books Found",Toast.LENGTH_SHORT).show()
                }

                for(i in 0 until itemArray.length()){
                    val itemObj = itemArray.getJSONObject(i)
                    val bookTitle: String = itemObj.getJSONObject("volumeInfo").
                    optString("title")

                    val bookImg: String = itemObj.getJSONObject("volumeInfo").getJSONObject("imageLinks")
                        .optString("thumbnail")

                    val publisher: String = itemObj.getJSONObject("volumeInfo").optString("publisher")
                    val publisherDate: String = itemObj.getJSONObject("volumeInfo").optString("publishedDate")
                    val previewLink: String = itemObj.getJSONObject("volumeInfo").optString("previewLink")
                    val infoLink: String = itemObj.getJSONObject("volumeInfo").optString("infoLink")
                    val bookDesc: String = itemObj.getJSONObject("volumeInfo").optString("description")
                    val pageCount: Int = itemObj.getJSONObject("volumeInfo").optInt("pageCount")

                    val bookObj = BookRVModal(bookTitle,bookImg,publisher,publisherDate,previewLink
                    ,bookDesc,infoLink,pageCount)

                    bookList = bookList+bookObj

                }
                bookRVAdapter = BookRVAdapter(bookList,this)
                booksRV.layoutManager = LinearLayoutManager(this)
                booksRV.adapter = bookRVAdapter
                bookRVAdapter.notifyDataSetChanged()

            }catch (e:JSONException){
                e.printStackTrace()
            }
        },
            {
                error->
                Toast.makeText(this,"Fail to get books",Toast.LENGTH_SHORT)
                    .show()
            }
            )
        queue.add(request)
    }
}