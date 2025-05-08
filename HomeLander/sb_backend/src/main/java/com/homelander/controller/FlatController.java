package com.homelander.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homelander.model.Flat;
import com.homelander.model.User;
import com.homelander.service.FlatService;
import com.homelander.service.UserService;

@RestController
@RequestMapping("/api/flats")
public class FlatController {

	@Autowired
	private FlatService flatService;
	
	@Autowired
	private UserService userService;
	
	//create a new flat
	@PostMapping("/create")
	public ResponseEntity<Flat> createFlat(@RequestBody Flat flat,@RequestHeader("userId") String userId){
		Flat createdFlat = flatService.createFlat(userId, flat);
		userService.updateUserFlatId(userId, createdFlat.getId());
		return ResponseEntity.ok(createdFlat);
	}
	
	//join an existing flat using join Code
	@PostMapping("/join")
	public ResponseEntity<Flat> joinFlat(@RequestParam String joinCode, @RequestHeader("userId") String userId){
		Flat joinedFlat = flatService.joinFlat(userId, joinCode);
		userService.updateUserFlatId(userId, joinedFlat.getId());
		return ResponseEntity.ok(joinedFlat);
	}
	
	//get all Flats
	@GetMapping
	public ResponseEntity<List<Flat>> getAllFlats(){
		List<Flat> li = flatService.getAllFlats();
		return ResponseEntity.ok(li);
	}
	//get details of a specific flat
	@GetMapping("/{flatId}"	)
	public ResponseEntity<Flat> getFlatInfo(@PathVariable String flatId){
		Flat flat = flatService.getFlatInfo(flatId);
		return ResponseEntity.ok(flat);
	}
	
	// update flat information
	@PatchMapping("/update")
	public ResponseEntity<Flat> updateFlat(@RequestHeader("flatId") String flatId, @RequestBody Flat flat,@RequestHeader("userId") String userId){
		Flat updatedFlat = flatService.updateFlat(userId, flatId, flat);
		return ResponseEntity.ok(updatedFlat);
	}
	
	// Leave a flat
	@PostMapping("/leave")
	public ResponseEntity<String> leaveFlat(@RequestHeader("flatId") String flatId,@RequestHeader("userId") String userId){
		flatService.leaveFlat(userId, flatId);
		userService.updateUserFlatId(userId, null);
		return ResponseEntity.ok("user left the flat");
	}
	
	//Remove a member from the flat (admin only )
	@DeleteMapping("/remove")
	public ResponseEntity<Void> removeMember(@RequestHeader("flatId") String flatId,
			@RequestParam("memberId") String memberId,@RequestHeader("userId") String adminId){
		flatService.removeMember(adminId, flatId, memberId);
		userService.updateUserFlatId(memberId, null);
		return ResponseEntity.ok().build();
	}
	
	// Delete the flat ( admin only )
	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteFlat(@RequestHeader("flatId") String flatId,@RequestHeader("userId") String adminId){
		flatService.deleteFlat(adminId, flatId);
		return ResponseEntity.ok().build();
	}
	
	//Get all members of a flat
	@GetMapping("/members")
	public ResponseEntity<List<User>> getAllMembers(@RequestHeader("flatId") String flatId){
		List<User> members = flatService.getAllMembers(flatId);
		return ResponseEntity.ok(members);
	}
}
