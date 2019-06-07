var students=[];
$( document).ready(function() {
	$.get( "http://localhost:8888/api/students", function( data ) {
		students = data;
		$.each(data , function(i, data) {
			  appendToUsrTable(data);
			});
		});
});

function getStudents(){
	$.get( "http://localhost:8888/api/students", function( data ) {
		return data;
		});
}
function flashMessage(msg) {
	  $(".flashMsg").remove();
	  $(".row").prepend(`
	        <div class="col-sm-12"><div class="flashMsg alert alert-success alert-dismissible fade in" role="alert"> <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button> <strong>${msg}</strong></div></div>
	    `);
	}
function deleteUser(id) {
	var url = 'http://localhost:8888/api/students/' + id;
	$.ajax({
	    url: url, 
	    type: 'DELETE',
	    success: function(result) {

	    	   var action = confirm("Are you sure you want to delete this user?");
	    	   var msg = "Student deleted successfully!";
	    	   students.forEach(function(student, i) {
	    	     if (student.id == id && action != false) {
	    	    	 students.splice(i, 1);
	    	       $("#studentTable #student-" + student.id).remove();
	    	       flashMessage(msg);
	    	     }
	    	   });
	    }
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
			 appendToUsrTable(response);
		}
	})
});
function editUser(id) {
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
			$("#studentTable #student-" +id).children("#studentData").each(function(){
				var attr = $(this).attr("name");
				if (attr == "name") {
					$(this).text = temp.name;
				} else if (attr == "address") {
					$(this).text = temp.address;
				} else if (attr == "age") {
					$(this).text = temp.age;
				}
			})
		}
	});
}

function appendToUsrTable(data) {
	  $("#studentTable").append(`
	        <tr id="student-${data.id}">
	            <td class="studentData" name="name">${data.name}</td>
	            '<td class="studentData" name="address">${data.age}</td>
	            '<td id="tdAge" class="studentData" name="age">${data.address}</td>
	            '<td align="center" class="operator">
	                <button class="btn btn-success form-control" onClick="editUser(${data.id})" data-toggle="modal" data-target="#exampleModal")">EDIT</button></td>
	              <td align="center" class="operator"> 
	               <button class="btn btn-danger form-control" onClick="deleteUser(${data.id})">DELETE</button>
	            </td>
	        </tr>`)};