package org.dbu.library.ui

import org.dbu.library.model.Book
import org.dbu.library.model.Patron
import org.dbu.library.repository.LibraryRepository
import org.dbu.library.service.BorrowResult
import org.dbu.library.service.LibraryService
import org.dbu.library.util.display

fun handleMenuAction(
    choice: String,
    service: LibraryService,
    repository: LibraryRepository
): Boolean {

    return when (choice) {

        "1" -> {
            addBook(service)
            true
        }

        "2" -> {
            registerPatron(repository)
            true
        }

        "3" -> {
            borrowBook(service)
            true
        }

        "4" -> {
            returnBook(service)
            true
        }

        "5" -> {
            search(service)
            true
        }

        "6" -> {
            listAllBooks(repository)
            true
        }

        "0" -> false

        else -> {
            println("Invalid option")
            true
        }
    }
}

// TODO: implement all required functions to handle the menu actions

fun addBook(service: LibraryService) {
    println("Enter ISBN:")
    val isbn = readln().trim()

    println("Enter Title:")
    val title = readln().trim()

    println("Enter Author:")
    val author = readln().trim()

    println("Enter Year:")
    val year = readln().toIntOrNull()
    if (year == null) {
        println("Invalid year!")
        return
    }

    val book = Book(isbn, title, author, year)
    service.addBook(book)
    println("Book added successfully.")
}

fun registerPatron(repository: LibraryRepository) {
    println("Enter Patron ID:")
    val id = readln().trim()

    println("Enter Patron Name:")
    val name = readln().trim()

    val patron = Patron(id, name)
    repository.addPatron(patron)
    println("Patron registered successfully.")
}

fun borrowBook(service: LibraryService) {
    println("Enter Patron ID:")
    val patronId = readln().trim()

    println("Enter ISBN:")
    val isbn = readln().trim()

    when (service.borrowBook(patronId, isbn)) {
        BorrowResult.SUCCESS ->
            println("Book borrowed successfully.")
        BorrowResult.BOOK_NOT_FOUND ->
            println("Book not found.")
        BorrowResult.PATRON_NOT_FOUND ->
            println("Patron not found.")
        BorrowResult.NOT_AVAILABLE ->
            println("Book is not available.")
        BorrowResult.LIMIT_REACHED ->
            println("Borrowing limit reached.")
    }
}

fun returnBook(service: LibraryService) {
    println("Enter Patron ID:")
    val patronId = readln().trim()

    println("Enter ISBN:")
    val isbn = readln().trim()

    val success = service.returnBook(patronId, isbn)
    if (success) println("Book returned successfully.") else println("Return failed.")
}

fun search(service: LibraryService) {
    println("Enter search keyword:")
    val query = readln().trim()

    val results = service.search(query)
    if (results.isEmpty()) {
        println("No books found.")
    } else {
        println("Search Results:")
        results.forEach { book ->
            println("${book.display()} | Available: ${book.isAvailable}")
        }
    }
}

fun listAllBooks(repository: LibraryRepository) {
    val books = repository.getAllBooks()
    if (books.isEmpty()) {
        println("No books available in the library.")
    } else {
        println("Library Books:")
        books.forEach { book ->
            println("${book.display()} | Available: ${book.isAvailable}")
        }
    }
}