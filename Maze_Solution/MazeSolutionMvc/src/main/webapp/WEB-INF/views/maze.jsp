<html>
<head>
<title>Maze Solution</title>
<!-- STYLE  SECTION -->
<style type="text/css">
textarea {
	background-color: #ffffe6;
}

table.example4 {
	background-color: transparent;
	border-collapse: collapse;
	width: 100%;
}

table.example4 th, table.example4 td {
	text-align: center;
	border: 0px solid black;
	padding: 5px;
}

table.example4 th {
	background-color: AntiqueWhite;
}

table.example4 th:first-child {
	width: 20%;
}
</style>



</head>
<body>

	<form action="/maze.do" method="post" name="mazeform">

		<table class="example4">
			<tr>

				<td><h1><b>Input a text of Maze Question</b></h1>
			</tr>
			<tr>
				<td><textarea name="mazeQuestion" id="mazeQuestion" cols="100" placeholder=" enter your maze question here.. " onfocus="this.placeholder = ''" onblur="this.placeholder = 'enter your maze question here.'"
						rows="30"></textarea></td>				
			</tr>
			<tr>
				<td><input type="submit" value="Solve Maze" /></td>
			</tr>


		</table>



	</form>
</body>
</html>


