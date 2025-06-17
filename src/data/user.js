import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { userAPI } from '@/services/api'

export const useUser = defineStore('user', () => {
  const users = ref([])
  const loading = ref(false)
  const error = ref(null)    // Fetch users from API
    const fetchUsers = async () => {
      try {
        loading.value = true
        error.value = null
        
        // Fetch from real API        // Fetch from real API
        const response = await userAPI.getAll()
        if (response.data.code === 1000) {
          const apiUsers = response.data.result
          users.value = apiUsers.map(user => ({
          id: user.id,
          name: `${user.first_name || user.firstName || ''} ${user.last_name || user.lastName || ''}`.trim(),
          email: user.email,
          username: user.username,
          dob: user.dob,
          phone: user.phone,
          role: user.roles && user.roles.length > 0 ? user.roles[0] : 'USER' // Take first role
        }))
      }
    } catch (err) {
      console.error('Error fetching users:', err)
      error.value = 'Failed to fetch users'

      // Fallback to demo data if API fails
      users.value = [
        {
          id: '1',
          name: 'Demo User',
          email: 'demo@example.com',
          username: 'demo',
          dob: '1990-01-01',
          phone: '1234567890',
          role: 'user'
        }
      ]
    } finally {
      loading.value = false
    }
  }

  const searchQuery = ref('')
  const filteredUsers = computed(() => {
    const q = searchQuery.value.toLowerCase()
    return users.value.filter(user =>
      user.name.toLowerCase().includes(q) ||
      user.email.toLowerCase().includes(q) ||
      user.username.toLowerCase().includes(q)
    )
  })

  const addUser = async (user) => {
      try {
        loading.value = true

        // Real API call
        const userData = {
          username: user.username,
          firstName: user.firstName || user.name?.split(' ')[0] || '',
          lastName: user.lastName || user.name?.split(' ').slice(1).join(' ') || '',
          email: user.email,
          phone: user.phone,
          dob: user.dob,
          role: user.role,
          password: user.password || 'defaultPassword123' // Should be handled properly
        }

      const response = await userAPI.create(userData)
      if (response.data.code === 1000) {
        // Refresh users list
        await fetchUsers()
      }
    } catch (err) {
      console.error('Error adding user:', err)
      error.value = 'Failed to add user'
    } finally {
      loading.value = false
    }
  }

  const updateUser = async (updatedUser) => {
      try {
        loading.value = true

        // Real API call
        const userData = {
          username: updatedUser.username,
          firstName: updatedUser.firstName || updatedUser.name?.split(' ')[0] || '',
          lastName: updatedUser.lastName || updatedUser.name?.split(' ').slice(1).join(' ') || '',
          email: updatedUser.email,
          phone: updatedUser.phone,
          dob: updatedUser.dob,
          role: updatedUser.role
        }

        const response = await userAPI.update(updatedUser.id, userData)
        if (response.data.code === 1000) {
          // Refresh users list
          await fetchUsers()
        }
    } catch (err) {
      console.error('Error updating user:', err)
      error.value = 'Failed to update user'
    } finally {
      loading.value = false
    }
  }

  const deleteUser = async (user) => {
      try {
        loading.value = true

        // Real API call
        const response = await userAPI.delete(user.id)
        if (response.data.code === 1000) {
          // Refresh users list
          await fetchUsers()
        }
    } catch (err) {
      console.error('Error deleting user:', err)
      error.value = 'Failed to delete user'
    } finally {
      loading.value = false
    }
  }
  return {
    users,
    items: users, // Add alias for consistency with BookStore
    searchQuery,
    filteredUsers,
    loading,
    error,
    fetchUsers,
    addUser,
    updateUser,
    deleteUser
  }
})
