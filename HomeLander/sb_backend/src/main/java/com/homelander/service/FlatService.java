package com.homelander.service;

import java.util.List;

import com.homelander.model.Flat;
import com.homelander.model.User;

public interface FlatService {
	Flat createFlat(String userId,Flat flat);
	
	Flat joinFlat(String userId, String joinCode);
	
	List<Flat> getAllFlats();
	
	Flat getFlatInfo(String FlatId);
	
	Flat updateFlat(String userId, String flatId,Flat updatedFlat);
	
	void leaveFlat(String userId,String FlatId);
	
	void removeMember(String adminId,String FlatId,String memberId);
	
	void deleteFlat(String adminId, String flatId);
	
	List<User> getAllMembers(String flatId);
}
