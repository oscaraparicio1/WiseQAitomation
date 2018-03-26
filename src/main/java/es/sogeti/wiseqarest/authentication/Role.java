package es.sogeti.wiseqarest.authentication;

public enum Role {

	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN");
	
	private String kind;
	
	Role(String kind){
		this.kind = kind;
	}
	
	public static Role find(String type) {
	    for (Role fileType : Role.values()) {
	        if (fileType.toString().contains(type.toLowerCase())) {
	            return fileType;
	        }
	    }
	    return USER;
	}
	
	@Override
	public String toString(){
		return this.kind;
	}
}