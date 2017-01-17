package com.proj2.mvc.dao;

import java.util.List;

import com.proj2.mvc.model.BlogComment;
import com.proj2.mvc.model.BlogPost;
import com.proj2.mvc.model.User;

public interface BlogDao {
	List<BlogPost> getBlogPosts();
	BlogPost getBlogPost(int id);
	BlogPost addBlogPost(User user,BlogPost blogPost);
	List<BlogComment> getBlogComments(int blogId);
	BlogPost addBlogPostComment(User user,BlogComment blogComment);
}
