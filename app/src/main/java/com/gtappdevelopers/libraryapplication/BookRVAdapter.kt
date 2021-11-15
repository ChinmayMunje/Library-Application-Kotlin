package com.gtappdevelopers.libraryapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class BookRVAdapter(private val bookRVList: List<BookRVModal>, private val context: Context) :
    RecyclerView.Adapter<BookRVAdapter.BookItemViewHolder>() {

    class BookItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookIV: ImageView = itemView.findViewById(R.id.idIVBook);
        val bookTitleTV: TextView = itemView.findViewById(R.id.idTVBookTitle);
        val bookPageCountTV: TextView = itemView.findViewById(R.id.idTVBookPageCount);
        val bookPublisherTV: TextView = itemView.findViewById(R.id.idTVBookPublisher);
        val bookPublisherDateTV: TextView = itemView.findViewById(R.id.idTVBookPublisherDate);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.book_rv_item, parent, false
        )
        return BookRVAdapter.BookItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        val bookRVItem = bookRVList[position]
        holder.bookTitleTV.text = bookRVItem.bookTitle
        holder.bookPageCountTV.text = "Number of Pages : " + bookRVItem.pageCount.toString()
        holder.bookPublisherTV.text = "Publisher : " + bookRVItem.publisher
        holder.bookPublisherDateTV.text = "Published On : " + bookRVItem.publishDate

        Picasso.get().load(bookRVItem.bookImg).placeholder(R.drawable.ic_app_icon)
            .into(holder.bookIV)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, BookDetailActivity::class.java)
            intent.putExtra("bookTitle", bookRVItem.bookTitle);
            intent.putExtra("bookPages", bookRVItem.pageCount.toString());
            intent.putExtra("bookPublisher", bookRVItem.publisher);
            intent.putExtra("bookPublishedDate", bookRVItem.publishDate);
            intent.putExtra("bookPreviewLink", bookRVItem.previewLink);
            intent.putExtra("bookDesc", bookRVItem.bookDesc);
            intent.putExtra("bookInfoLink", bookRVItem.infoLink);
            intent.putExtra("bookImg", bookRVItem.bookImg);
            context.startActivity(intent);
        }
    }

    override fun getItemCount(): Int {
        return  bookRVList.size
    }


}