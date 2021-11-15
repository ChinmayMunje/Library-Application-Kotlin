package com.gtappdevelopers.libraryapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class BookDetailActivity : AppCompatActivity() {
    lateinit var bookTitleTV: TextView
    lateinit var bookPublisherTV: TextView
    lateinit var bookPublisherDateTV: TextView
    lateinit var bookPagesTV: TextView
    lateinit var bookDescTV: TextView
    lateinit var bookIV: ImageView
    lateinit var buyBookBtn: Button

    lateinit var bookTitle: String
    lateinit var bookPublisher: String
    lateinit var bookPublisherDate: String
    lateinit var bookPages: String
    lateinit var bookDesc: String
    lateinit var bookImgUrl: String
    lateinit var previewLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)
        bookTitle = intent.getStringExtra("bookTitle").toString();
        bookPublisher = intent.getStringExtra("bookPublisher").toString();
        bookPublisherDate = intent.getStringExtra("bookPublishedDate").toString();
        bookPages = intent.getStringExtra("bookPages").toString();
        bookDesc = intent.getStringExtra("bookDesc").toString();
        bookImgUrl = intent.getStringExtra("bookImg").toString();
        previewLink = intent.getStringExtra("bookPreviewLink").toString();

        bookTitleTV = findViewById(R.id.idTVBookTitle)
        bookPublisherTV = findViewById(R.id.idTVBookPublisher)
        bookPublisherDateTV = findViewById(R.id.idTVBookPublisherDate)
        bookPagesTV = findViewById(R.id.idTVBookPageCount)
        bookDescTV = findViewById(R.id.idTVBookDescription)
        bookIV = findViewById(R.id.idIVBook)
        buyBookBtn = findViewById(R.id.idBtnBuyBook)

        bookTitleTV.text = bookTitle
        bookPublisherTV.text = bookPublisher
        bookPublisherDateTV.text = bookPublisherDate
        bookPagesTV.text = bookPages
        bookDescTV.text = bookDesc

        Picasso.get().load(bookImgUrl).placeholder(R.drawable.ic_app_icon).into(bookIV);
        buyBookBtn.setOnClickListener {
            val openUrl = Intent(Intent.ACTION_VIEW)
            openUrl.data = Uri.parse(previewLink)
            startActivity(openUrl)
        }

    }
}