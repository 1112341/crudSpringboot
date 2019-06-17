var students=[];
function onExport() {
	$.get( "http://localhost:8888/api/student/export", function( data ) {
		console.log(data);
		});
	$.ajax
}
$( document).ready(function() {
	$.get( "http://localhost:8888/api/students", function( data ) {
		students = data;
		$.each(data , function(i, data) {
			  appendToStudentTable(data);
			});
		});
});

function getStudents(){
	$.get( "http://localhost:8888/api/students", function( data ) {
		return data;
		});
}

function deleteStudent(id) {
	var flat = false;
	var url = 'http://localhost:8888/api/students/' + id;
	 var action = confirm("Are you sure you want to delete this user?");
	students.forEach(function(student, i) {
   	
	 if (student.id == id && action != false) {
	$.ajax({
	    url: url, 
	    type: 'DELETE',
	    success: function(result) {

	    	   
	    	   
	    	     if (student.id == id && action != false) {
	    	    	
	  	    	   var msg = "Student deleted successfully!";
	    	    	// students.splice(i, 1);
	    	       $("#studentTable #user-" + student.id).remove();
	    	       flat = true;
	    	      // flashMessage(msg);
	    	     }}}
	    	   );
	    }
	 if (flat) return true;
	});
	
};

$("form").submit(function(e) {
	  e.preventDefault();
	});

$("form#addStudent").submit(function(){
	var student={};
	var name = $('input[name="name"]').val().trim();
	var address = $('input[name = "address"').val().trim();
	var age = $('input[name = "age"]').val().trim();

	student.name = name;
	student.address = address;
	student.age = age;

	$.ajax({
		url : 'http://localhost:8888/api/students',
		contentType: "application/json",
		type:'POST',
		data: JSON.stringify(student),
		success : function (response) {
			
			
			console.log(response);
			students.push(response);
			console.log(students);
			 appendToStudentTable(response);
		}
	})
});
function editStudent(id) {
	students.forEach(function(student, i) {
		if (student.id === id) {
			$(".modal-body").empty().append(`
				 <form id="updateStudent" action="">
                    <label for="name">Name</label>
                    <input class="form-control" type="text" name="name" value="${student.name}"/>
                    <label for="address">Address</label>
                    <input class="form-control" type="text" name="address" value="${student.address}"/>
                    <label for="age">Age</label>
                    <input class="form-control" type="number" name="age" value="${student.age}" min=10 max=100/>
            `
		)}
	});
	$(".modal-footer").empty().append(`
            <button  type="button" class="btn btn-primary" onClick="updateStudent(${id})">Save changes</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </form>
    `);
		    
		  
}

function updateStudent(id) {
	  var msg = "User updated successfully!";
	console.log("update Student");
	var temp = {};
	temp.id = id;
	students.forEach(function (student, i){
		if(student.id === id ){
	
			$("#updateStudent").children("input").each(function(){
				console.log("test");
				var value = $(this).val();
		        var attr = $(this).attr("name");
				if (attr == "name") {
					temp.name = value;
				} else if (attr == "address") {
					temp.address = value;
				} else if (attr == "age"){
					temp.age = value;
				}
			});
			students.splice(i,1,temp);
			console.log(students);
		}
	});
	console.log(temp);
	$.ajax({
		url:"http://localhost:8888/api/students/" + id,
		contentType: "application/json",
		type:'PUT',
		data : JSON.stringify(temp),
		success : function (response) {
			console.log(response);
			console.log("update table");
		      $("#studentTable #user-" + id).children(".userData").each(function() {
		          var attr = $(this).attr("name");
		          if (attr == "name") {
		            $(this).text(temp.name);
		          } else if (attr == "address") {
		            $(this).text(temp.address);
		          } else {
		            $(this).text(temp.age);
		          }
		        });
			$(".modal").modal("toggle");
		      
		}
	});
}
function appendToStudentTable(student) {
	  $("#studentTable > tbody:last-child").append(`
	        <tr id="user-${student.id}">
            <td class="userData" name="name">${student.name}</td>
            '<td class="userData" name="address">${student.address}</td>
            '<td id="tdAge" class="userData" name="age">${student.age}</td>
            '<td align="center">
                <button class="btn btn-success form-control" onClick="editStudent(${student.id})" data-toggle="modal" data-target="#exampleModal")">EDIT</button>
            </td>
            <td align="center">
                <button class="btn btn-danger form-control" onClick="deleteStudent(${student.id})">DELETE</button>
            </td>
	        </tr>`)};
