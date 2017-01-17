package com.proj2.mvc.implementation;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.proj2.mvc.dao.BlogDao;
import com.proj2.mvc.model.BlogComment;
import com.proj2.mvc.model.BlogPost;
import com.proj2.mvc.model.User;
@Repository
public class BlogDaoImpl implements BlogDao {
	@Autowired
private SessionFactory sessionFactory;
	@Override
	public List<BlogPost> getBlogPosts() {
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from BlogPost");
		List<BlogPost> blogPosts=query.list();
		session.close();
		return blogPosts;
	}

	@Override
	public BlogPost getBlogPost(int id) {
		Session session=sessionFactory.openSession();
		BlogPost blogPost=(BlogPost)session.get(BlogPost.class, id);
		session.close();
		return blogPost;
	}

	@Override
	public BlogPost addBlogPost(User user, BlogPost blogPost) {
		Session session=sessionFactory.openSession();
		blogPost.setCreatedBy(user);
		blogPost.setCreatedOn(new Date());
		session.save(blogPost);
		session.flush();
		BlogPost addedBlogPost=(BlogPost)session.get(BlogPost.class, blogPost.getId());
		return addedBlogPost;
	}

	@Override
	public BlogPost addBlogPostComment(User user, BlogComment blogComment) {
		Session session=sessionFactory.openSession();
	 blogComment.setCreatedBy(user);
	 blogComment.setCreatedOn(new Date());
	 BlogPost blogPost=(BlogPost)session.get(BlogPost.class, blogComment.getBlogPost().getId());
			 blogComment.setBlogPost(blogPost);
	 session.merge(blogComment);
	 session.flush();
	 session.close();
	 return blogPost;
	 
	}

	@Override
	public List<BlogComment> getBlogComments(int blogId) {
		Session session=sessionFactory.openSession();
		BlogPost blogPost=(BlogPost)session.get(BlogPost.class, blogId);
		List<BlogComment> blogComments=blogPost.getComments();
		session.close();
		return blogComments;
	}


}
