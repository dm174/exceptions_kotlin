class PostNotFoundException(message: String) : Exception(message)
data class Post(
    var id: Int,

    var likes: Likes = Likes(2),
    var stringData: String = "",
    var booleanData: Boolean = false,
    var byteData: Byte = 0,
    var shortData: Short = 0,
    var intData: Int = 0,
    var longData: Long = 0L,
    var floatData: Float = 0.0f,
    var doubleData: Double = 0.0,
    var charData: Char = ' ',


    )

data class Likes(
    var count: Int
)

data class Comment(
    var id: Int,
    var postId: Int,
    var text: String
)

object WallService {

    private var comments = emptyArray<Comment>()
    private var posts = emptyArray<Post>()
    private var lastId = 0

    fun getLastId(): Int {
        return lastId
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        val post = posts.find { it.id == postId }
        return if (post != null) {
            val newComment = comment.copy(id = ++lastId)
            comments += newComment
            newComment
        } else {
            throw PostNotFoundException("Post with ID $postId not found")
        }
    }


    fun add(post: Post): Post {
        val newPost = post.copy(id = ++lastId, likes = post.likes.copy())
        posts += newPost
        return newPost


    }

    fun update(newPost: Post): Boolean {
        for (index in posts.indices) {
            if (posts[index].id == newPost.id) {
                posts[index] = newPost.copy(likes = newPost.likes.copy())
                return true
            }
        }
        return false
    }


    fun getById(id: Int): Post? {
        return posts.find { it.id == id }
    }


    fun clear() {
        posts = emptyArray()
        comments = emptyArray()
        lastId = 0// также здесь нужно сбросить счетчик для id постов, если он у вас используется
    }

    fun print() {
        for (post in posts) {
            print(post)
            print(' ')
        }
        println()
    }
}


fun main() {
    val post = Post(1)
    WallService.add(post)
    WallService.add(Post(1))
    WallService.add(Post(2))
     WallService.print()
    post.id = 2
     WallService.print()
    WallService.update(Post(2, Likes(12)))
     WallService.print()

    val comment = Comment(5, 5, "Hello!")
    try {
        val createdComment = WallService.createComment(3, comment)
        println("Добавлен комментарий: $createdComment")

    } catch (e: PostNotFoundException) {
        println(e.message)
    }
    WallService.clear()
    WallService.print()

}