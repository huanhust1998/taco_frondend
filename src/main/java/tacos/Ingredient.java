package tacos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public class Ingredient {
	private String id;
	private String name;
	private Type type;
	public static enum Type {
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}	
}

