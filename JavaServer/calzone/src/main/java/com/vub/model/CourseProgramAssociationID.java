package com.vub.model;

import java.io.Serializable;

public class CourseProgramAssociationID implements Serializable {

	private int courseID;
	private int programID;
	
	/* The serialization runtime associates with each serializable class a version number, 
	 * called a serialVersionUID, which is used during deserialization to verify that the 
	 * sender and receiver of a serialized object have loaded classes for that object that 
	 * are compatible with respect to serialization. If the receiver has loaded a class for 
	 * the object that has a different serialVersionUID than that of the corresponding sender's class, 
	 * then deserialization will result in an InvalidClassException. A serializable class can 
	 * declare its own serialVersionUID explicitly by declaring a field named "serialVersionUID" 
	 * that must be static, final, and of type long: */
	private static final long serialVersionUID=100L;

	@Override
	public int hashCode() {
		return courseID + programID;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof CourseProgramAssociationID) {
			CourseProgramAssociationID otherId = (CourseProgramAssociationID) object;
			return (otherId.courseID == this.courseID) && (otherId.programID == this.programID);
		}
		return false;
	}
}