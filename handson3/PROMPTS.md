Session 1.1:-
Part A :-
Ghost text: 
    public record UserDTO(String id, String name, String email) {
        public static UserDTO fromUser(User user) {
            return new UserDTO(user.id(), user.name(), user.email());
        }
    }

After re-triggering:
    public record UserDTO (long id, String name, String email, boolean active) {
        public static UserDTO fromUser(User user) {
            return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.isActive());
        }
    }


Part B :-
No Compilation error

Part C :-
AI generated was good, seemed to be aligning with the hand-written one if not better.

Part D:-
Commit Message : Implement order management endpoints and enhance UserDTO structure
