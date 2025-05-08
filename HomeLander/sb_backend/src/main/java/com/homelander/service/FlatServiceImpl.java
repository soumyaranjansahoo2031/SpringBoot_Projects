package com.homelander.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.homelander.model.Flat;
import com.homelander.model.User;
import com.homelander.repository.FlatRepository;

@Service
public class FlatServiceImpl implements FlatService{
	
	@Autowired
	private FlatRepository flatRepository;
	
	@Autowired
	private UserService userService;
	
	public Flat createFlat(String userId,Flat flat) {
		flat.setCreatedBy(userId);
		flat.setMemberIds(new ArrayList<>(List.of(userId)));
		flat.setJoinCode(UUID.randomUUID().toString().substring(0,6));
		Flat createdFlat = flatRepository.save(flat);
		userService.updateUserFlatId(userId, createdFlat.getId());
		return createdFlat;
	}
	public Flat joinFlat(String userId, String joinCode){
		Flat flat = flatRepository.findByJoinCode(joinCode)
				.orElseThrow(()-> new RuntimeException("Invalid joinCode"));
		if(!flat.getMemberIds().contains(userId)) {
			flat.getMemberIds().add(userId);
			flatRepository.save(flat);
			userService.updateUserFlatId(userId, flat.getId());
		}
		else {
//			ResponseEntity.ok("User is already a member of this flat");
			System.out.println("User is already a member of this flat");
		}
		return flat;
	}
	public List<Flat> getAllFlats() {
		return flatRepository.findAll();
	}
	public Flat getFlatInfo(String flatId) {
		return flatRepository.findById(flatId)
				.orElseThrow(()-> new RuntimeException("Flat Not Found"));
	}
	public Flat updateFlat(String userId,String flatId,Flat updatedFlat) {
		Flat flat = getFlatInfo(flatId);
		if(!flat.getCreatedBy().equals(userId)) {
			throw new RuntimeException("Only admin can update the flatinfo");
		}
		flat.setName(updatedFlat.getName());
		return flatRepository.save(flat);
	}
	public void leaveFlat(String userId,String flatId) {
		Flat flat = getFlatInfo(flatId);
		if(flat.getCreatedBy().equals(userId)) {
			throw new RuntimeException("admin can not leave the flat");
		}
		flat.getMemberIds().remove(userId);
		flatRepository.save(flat);
		userService.updateUserFlatId(userId, null);
	}
	public void removeMember(String adminId,String flatId,String memberId ) {
		Flat flat = getFlatInfo(flatId);
		if(!flat.getCreatedBy().equals(adminId)) {
			throw new RuntimeException("Only admin can remove someone from the flat");
		}
		flat.getMemberIds().remove(memberId);
		flatRepository.save(flat);
		userService.updateUserFlatId(memberId, null);
	}
	public void deleteFlat(String adminId, String flatId) {
		Flat flat = getFlatInfo(flatId);
		if(!flat.getCreatedBy().equals(adminId)) {
			throw new RuntimeException("Only admin can delete the flat");
		}
		for(String memberId : flat.getMemberIds()) {
			userService.updateUserFlatId(memberId, null);
		}
		flatRepository.delete(flat);
	}
	public List<User> getAllMembers(String flatId){
		List<User> li = new ArrayList<>();
		Flat flat = getFlatInfo(flatId);
		for(String userId : flat.getMemberIds()) {
			User user = userService.getUserById(userId);
			li.add(user);
		}
		return li;
	}
}
