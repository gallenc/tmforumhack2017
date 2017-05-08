<html>
<body>
	<h2>tmforum-address-gis-distance</h2>

	<p>This service provides methods to calculate distances between
		points and find closest addresses to a given point</p>

	<table>
		<tr>
			<th>description</th>
			<th>example</th>
		</tr>
		<tr>
			<td>Find nearest address to a point latitude_start=50.889311&longitude_start=-1.391915</td>
			<td><a
				href="/tmforum-address-gis-distance/gisaddress/api/v1/nearestAddress?latitude_start=50.889311&longitude_start=-1.391915">try
					it</a></td>
		</tr>
				<tr>
			<td>Find all closest addresses to a point. </td>
			<td><a
				href="/tmforum-address-gis-distance/gisaddress/api/v1/closestAddresses?latitude_start=50.889311&longitude_start=-1.391915">try
					it</a></td>
		</tr>
		
		<tr>
			<td>Find all closest addresses to a point.Address must have a given
				street name &streetName=Itchen Quays</td>
			<td><a
				href="/tmforum-address-gis-distance/gisaddress/api/v1/closestAddresses?latitude_start=50.889311&longitude_start=-1.391915&streetName=Itchen%20Quays">try
					it</a></td>
		</tr>
		<tr>
			<td>Find nearest address to a point. Limit number of addresses
				returned &maxReturnAddresses=3</td>
			<td><a
				href="/tmforum-address-gis-distance/gisaddress/api/v1/closestAddresses?latitude_start=50.889311&longitude_start=-1.391915&maxReturnAddresses=3
 " >try it</a></td>
		</tr>

		<tr>
			<td>Calculate the distance between 2 points a and b given lat
				and long gis coordinates for a and b</td>
			<td><a
				href="/tmforum-address-gis-distance/gisaddress/api/v1/distance?latitude_start=50.889311&longitude_start=-1.391915&latitude_finish=50.891099&longitude_finish=-1.390925
 ">try
					it</a></td>
		</tr>
	</table>

</body>
</html>
