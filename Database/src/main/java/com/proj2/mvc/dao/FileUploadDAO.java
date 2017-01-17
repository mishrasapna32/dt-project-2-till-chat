package com.proj2.mvc.dao;

import com.proj2.mvc.model.UploadFile;

public interface FileUploadDAO {
	void save(UploadFile uploadFile);
	UploadFile getFile(String username);
}
