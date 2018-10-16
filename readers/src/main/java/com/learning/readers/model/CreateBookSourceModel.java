package com.learning.readers.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.learning.readers.validator.FileRequiredIf;
import com.learning.readers.validator.NotBlankIf;

/*@NotBlankIf(
		baseField = "bookTypeId", value = "3", 
		matchField = "value",
		message="eBook URL is mandatory if it's an eBook")
@FileRequiredIf(
		baseField = "bookTypeId", value = "2", 
		matchField = "bookFile",
		message="Please select book file to upload, if it's an eBook")*/
public class CreateBookSourceModel {

	private Integer id;
	private List<BookTypeValueModel> bookTypeList = new ArrayList<>();
	private Integer bookTypeId;
	private String value;
	private MultipartFile bookFile;
	
	public CreateBookSourceModel() {}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBookTypeId() {
		return bookTypeId;
	}
	public void setBookTypeId(Integer bookTypeId) {
		this.bookTypeId = bookTypeId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public MultipartFile getBookFile() {
		return bookFile;
	}

	public void setBookFile(MultipartFile bookFile) {
		this.bookFile = bookFile;
	}

	public List<BookTypeValueModel> getBookTypeList() {
		return bookTypeList;
	}

	public void setBookTypeList(List<BookTypeValueModel> bookTypeList) {
		this.bookTypeList = bookTypeList;
	}

	@Override
	public String toString() {
		return "CreateBookSourceModel [id=" + id + ", bookTypeId=" + bookTypeId + ", value=" + value + "]";
	}
	
	
}
