// stores/bookStore.js
import { defineStore } from 'pinia'
import { ref, reactive, computed } from 'vue'
import { bookAPI } from '@/services/api'

export const useBook = defineStore('book', () => {
  const items = ref([])
  const loading = ref(false)
  const error = ref(null)
  const fullBookDetails = reactive({})

  // Fetch books from API
  const fetchBooks = async () => {
    try {
      loading.value = true
      error.value = null

      // Fetch from real API
      const response = await bookAPI.getAll()
      if (response.data.code === 1000) {
        const books = response.data.result
        items.value = books.map(book => ({
          id: book.id,
          title: book.title,
          type: book.category || 'Unknown',
          import_price: book.price ? book.price.toString() : '0',
          author: book.author,
          quantity: book.totalCopies ? book.totalCopies.toString() : '0',
          published_year: book.publishYear ? book.publishYear.toString() : 'Unknown',
          isbn: book.isbn,
          publisher: book.publisher,
          description: book.description,
          available_copies: book.availableCopies ? book.availableCopies.toString() : '0'
        }))

        // Set full details for each book
        books.forEach(book => {
          fullBookDetails[book.id] = {
            id: book.id,
            title: book.title,
            type: book.category || 'Unknown',
            import_price: book.price ? book.price.toString() : '0',
            author: book.author,
            quantity: book.totalCopies ? book.totalCopies.toString() : '0',
            published_year: book.publishYear ? book.publishYear.toString() : 'Unknown',
            categories: book.category ? [book.category] : ['Unknown'],
            description: book.description || 'No description provided',
            publisher: book.publisher || 'Unknown',
            isbn: book.isbn || 'Unknown',
            available_copies: book.availableCopies ? book.availableCopies.toString() : '0'
          }
        })
      }
    } catch (err) {
      console.error('Error fetching books:', err)
      error.value = 'Failed to fetch books'

      // Fallback to demo data if API fails
      items.value = [
        {
          id: '1',
          title: 'Spring Boot Programming (Demo)',
          type: 'Technology',
          import_price: '250000',
          author: 'John Doe',
          quantity: '50',
          published_year: '2024',
        }
      ]
    } finally {
      loading.value = false
    }
  }

  const searchQuery = ref('')
  const filteredBooks = computed(() => {
    const q = searchQuery.value.toLowerCase()
    return items.value.filter(book =>
      book.id.toLowerCase().includes(q) ||
      book.title.toLowerCase().includes(q)
    )
  }) // Thêm dấu đóng ngoặc và dấu phẩy

  const addBook = async (newBook) => {
    try {
      loading.value = true        // Real API call
        const bookData = {
          title: newBook.title,
          author: newBook.author,
          isbn: newBook.isbn || null,
          publisher: newBook.publisher || null,
          publishYear: newBook.published_year ? parseInt(newBook.published_year) : null,
          category: newBook.type,
          price: newBook.import_price ? parseFloat(newBook.import_price) : null,
          totalCopies: newBook.quantity ? parseInt(newBook.quantity) : null,
          availableCopies: newBook.quantity ? parseInt(newBook.quantity) : null,
          description: newBook.description || null
        }

      const response = await bookAPI.create(bookData)
      if (response.data.code === 1000) {
        // Refresh books list
        await fetchBooks()
      }
    } catch (err) {
      console.error('Error adding book:', err)
      error.value = 'Failed to add book'
    } finally {
      loading.value = false
    }
  }

  const updateBook = async (updatedBook) => {
    try {
      loading.value = true      // Real API call
      const bookData = {
        title: updatedBook.title,
        author: updatedBook.author,
        isbn: updatedBook.isbn || null,
        publisher: updatedBook.publisher || null,
        publishYear: updatedBook.published_year ? parseInt(updatedBook.published_year) : null,
        category: updatedBook.type,
        price: updatedBook.import_price ? parseFloat(updatedBook.import_price) : null,
        totalCopies: updatedBook.quantity ? parseInt(updatedBook.quantity) : null,
        availableCopies: updatedBook.available_copies ? parseInt(updatedBook.available_copies) : null,
        description: updatedBook.description || null
      }

      const response = await bookAPI.update(updatedBook.id, bookData)
      if (response.data.code === 1000) {
        // Refresh books list
        await fetchBooks()
      }
    } catch (err) {
      console.error('Error updating book:', err)
      error.value = 'Failed to update book'
    } finally {
      loading.value = false
    }
  }

  const deleteBook = async (book) => {
    try {
      loading.value = true

      // Real API call
      const response = await bookAPI.delete(book.id)
      if (response.data.code === 1000) {
        // Refresh books list
        await fetchBooks()
      }
    } catch (err) {
      console.error('Error deleting book:', err)
      error.value = 'Failed to delete book'
    } finally {
      loading.value = false
    }
  }
  const searchBooks = (query) => {
    if (!query) return items.value
    const q = query.toLowerCase()
    return items.value.filter(book =>
      book.id.toLowerCase().includes(q) ||
      book.title.toLowerCase().includes(q) ||
      book.author.toLowerCase().includes(q) ||
      book.type.toLowerCase().includes(q)
    )
  }

  return {
    items,
    fullBookDetails,
    searchQuery,
    filteredBooks,
    searchBooks,
    loading,
    error,
    fetchBooks,
    addBook,
    updateBook,
    deleteBook
  }
})
