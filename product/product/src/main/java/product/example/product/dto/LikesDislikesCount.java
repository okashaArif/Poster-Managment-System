package product.example.product.dto;

public class LikesDislikesCount {
    private long likes;
    private long dislikes;

    public LikesDislikesCount(long likes, long dislikes) {
        this.likes = likes;
        this.dislikes = dislikes;
    }

    // Getters and setters
    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getDislikes() {
        return dislikes;
    }

    public void setDislikes(long dislikes) {
        this.dislikes = dislikes;
    }
}
