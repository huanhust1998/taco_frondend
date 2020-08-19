package tacos;

import lombok.Data;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Order {
	private Long id;
	private Date placedAt;
	private String name;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String ccNumber;
	private String ccExpiration;
	private String ccCVV;
	private List<Taco> tacos = new ArrayList<>();
}