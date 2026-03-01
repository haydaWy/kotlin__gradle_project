package org.dbu.library.repository

import org.dbu.library.model.Book
import org.dbu.library.model.Patron

class InMemoryLibraryRepository : LibraryRepository {

    private val books: MutableMap<String, Book> = LinkedHashMap()
    private val patrons: MutableMap<String, Patron> = LinkedHashMap()

    // TODO: Implement the methods to manage books and patrons in memory

     override fun addBook(book: Book): Boolean {
        return books.putIfAbsent(book.isbn, book) == null
    }

    override fun findBook(isbn: String): Book? {
        return books[isbn]
    }

    override fun updateBook(book: Book) {
        books[book.isbn] = book
    }

    override fun addPatron(patron: Patron): Boolean {
        return patrons.putIfAbsent(patron.id, patron) == null
    }

    override fun findPatron(id: String): Patron? {
        return patrons[id]
    }

    override fun updatePatron(patron: Patron) {
        patrons[patron.id] = patron
    }

    override fun getAllBooks(): List<Book> {
        return books.values.toList()
    }
}