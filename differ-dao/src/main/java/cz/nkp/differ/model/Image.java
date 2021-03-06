package cz.nkp.differ.model;

import java.io.File;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author xrosecky
 */
@Entity
@Table(name = Image.TABLE)
public class Image implements Serializable {

    public static final String TABLE = "image";
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "filename")
    private String fileName;
    
    @Column(name = "unique_name")
    private String uniqueName;
    
    @Column(name = "owner_id")
    private Long ownerId;
    
    @Column(name = "file_size")
    private int size;
    
    @Column(name = "shared")
    private boolean shared = false;
    
    @Transient
    private File file;

    public Image() {
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public String getUniqueName() {
	return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
	this.uniqueName = uniqueName;
    }

    public Long getOwnerId() {
	return ownerId;
    }

    public void setOwnerId(Long ownerId) {
	this.ownerId = ownerId;
    }

    public boolean isShared() {
	return shared;
    }

    public void setShared(boolean shared) {
	this.shared = shared;
    }

    public int getSize() {
	return size;
    }

    public void setSize(int size) {
	this.size = size;
    }

    public File getFile() {
	return file;
    }

    public void setFile(File file) {
	this.file = file;
    }
    
}
