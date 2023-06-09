import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class MainKtTest {


    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }
    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        val comment = Comment(1, 1, "Hello, world!")
        WallService.createComment(3, comment)
    }
    @Test
    fun testCreateComment() {
        val post = Post(1)
        WallService.add(post)

        val comment = Comment(2, post.id, "Hello, world!")
        val createdComment = WallService.createComment(post.id, comment)

        assertNotNull(createdComment)
        assertEquals(comment.id, createdComment.id)
        assertEquals(comment.postId, createdComment.postId)
        assertEquals(comment.text, createdComment.text)
    }
    @Test
    fun testAddPost() {
        val post = Post(0)
        val addedPost = WallService.add(post)
        assertNotEquals(0, addedPost.id)
    }

    @Test
    fun testUpdateExistingPost7() {
        val post = Post(0)
        WallService.add(post)
        val updatedPost = Post(post.id, Likes(12))
        val result = WallService.update(updatedPost)
        assertFalse(result)
    }

    @Test
    fun testUpdateExistingPost1() {
        val post = Post(2)
        WallService.add(post)
        val updatedPost = Post(post.id, Likes(12))
        val result = WallService.update(updatedPost)
        assertFalse(result)
    }

    @Test
    fun testUpdateNonExistingPost() {
        val updatedPost = Post(2, Likes(12))
        val result = WallService.update(updatedPost)
        assertFalse(result)
    }

    @Test
    fun testGetPostById() {
        val post = Post(0)
        WallService.add(post) // Добавляем пост в WallService
        val retrievedPost = WallService.getById(post.id)
        assertNull(retrievedPost)
    }

    @Test
    fun testGetNonExistingPostById() {
        val retrievedPost = WallService.getById(999)
        assertNull(retrievedPost)
    }

    @Test
    fun testUpdateNonExistingPostNotSaved() {
        val updatedPost = Post(2, Likes(12))
        WallService.update(updatedPost)
        val retrievedPost = WallService.getById(updatedPost.id)
        assertNull(retrievedPost)
    }

    @Test
    fun testGetNonExistingPostByIdReturnsNull() {
        val retrievedPost = WallService.getById(999)
        assertNull(retrievedPost)
    }

    @Test
    fun main1() {

        main()

    }


    @Test
    fun testUpdateNonExistingPost6() {
        val updatedPost = Post(2, Likes(12)) // Создаем новый пост с обновленными данными
        val result = WallService.update(updatedPost) // Обновляем пост в WallService
        assertFalse(result) // Проверяем, что обновление не удалось, так как пост с таким ID не существует
        val retrievedPost = WallService.getById(updatedPost.id) // Получаем пост из WallService по заданному ID
        assertNull(retrievedPost) // Проверяем, что полученный пост является null
    }


    @Test
    fun testClearPosts() {
        val post = Post(1)
        WallService.add(post)
        WallService.clear()
        val retrievedPost = WallService.getById(post.id)
        assertNull(retrievedPost)
    }

    @Test
    fun testUpdateExistingPost() {
        val post = Post(0)
        WallService.add(post)
        val updatedPost = Post(1, Likes(12))
        val result = WallService.update(updatedPost)
        assertTrue(result)
    }
    @Test
    fun testClearPostsAndLastId() {
        val post1 = Post(1)
        val post2 = Post(2)
        WallService.add(post1)
        WallService.add(post2)
        WallService.clear()
        val retrievedPost1 = WallService.getById(post1.id)
        val retrievedPost2 = WallService.getById(post2.id)
        assertNull(retrievedPost1)
        assertNull(retrievedPost2)
        assertEquals(0, WallService.getLastId())
    }



}