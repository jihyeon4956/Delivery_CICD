package com.example.miniprojectdelivery.enums;

public enum UserRoleEnum {

    CUSTOMER(Authority.CUSTOMER),  // 사용자 권한, 생성자의 값을 넣어준다.
    OWNER(Authority.OWNER);  // 관리자 권한

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String CUSTOMER = "ROLE_CUSTOMER";
        public static final String OWNER = "ROLE_OWNER";
    }
}
