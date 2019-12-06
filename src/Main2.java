import java.util.Date;
import java.util.Scanner;

public class Main2 {
    
    public static void main(String[] args) {
        run();
    }
    
    private static void printOptions() {
        System.out.println("ADMINISTRACJA");
        System.out.println("Wybierz opcję:");
        System.out.println("1 - edycja użytkowników");
        System.out.println("2 - edycja zadań");
        System.out.println("3 - zarządzanie grupami");
        System.out.println("4 - przypisywanie zadań");
        System.out.println("0 - zakończ program");
    }
    
    private static void run() {
        Scanner scanner = new Scanner(System.in);
        printOptions();
        
        while (true) {
            String input = scanner.nextLine();
            int option;
            try {
                option = Integer.parseInt(input);
                switch (option) {
                    case 0:
                        return;
                    case 1:
                        adminUsers(scanner);
                        printOptions();
                        break;
                    case 2:
                        adminExercises(scanner);
                        printOptions();
                        break;
                    case 3:
                        adminGroups(scanner);
                        printOptions();
                        break;
                    case 4:
                        adminSolutions(scanner);
                        printOptions();
                        break;
                    default: {
                        System.out.println("Nie wybrano poprawnej opcji. Wpisz ponownie");
                        printOptions();
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Wybierz jedną z dostępnych opcji.");
                printOptions();
            }
        }
    }
    
    
    private static void printUsers() {
        UserDao userDao = new UserDao();
        User[] users = userDao.findAll();
        System.out.println("Lista użytkowników:");
        for (User user : users) {
            System.out.print("ID." + user.getId() + " ");
            System.out.println(user.getUser_name() + ", e-mail: " + user.getEmail());
        }
    }
    
    private static void adminUsers(Scanner scanner) {
        while (true) {
            printUsers();
            System.out.println("-----------------");
            System.out.println("Wybierz jedną z opcji \nadd - dodanie użytkownika");
            System.out.println("edit - edycja użytkownika\ndelete - usunięcie użytkownika");
            System.out.println("quit - zakończenie programu");
            
            int option = getOption(scanner);
            switch (option) {
                case 0: {
                    System.out.println("Zakończono edycję użytkowników");
                    return;
                }
                case 1: {
                    addUser(scanner);
                    break;
                }
                case 2: {
                    editUser(scanner);
                    break;
                }
                case 3: {
                    deleteUser(scanner);
                    break;
                }
            }
        }
        //powrót do początku
    }
    
    private static int getOption(Scanner scanner) {
        String input = scanner.nextLine();
        while (true) {
            switch (input) {
                case "quit":
                    return 0;
                case "add":
                    return 1;
                case "edit":
                    return 2;
                case "delete":
                    return 3;
                default: {
                    System.out.println("Nie wybrano poprawnej opcji. Wpisz ponownie");
                    input = scanner.nextLine();
                }
            }
        }
    }
    
    private static void addUser(Scanner scanner) {
        System.out.println("Podaj nazwę dla nowego użytkownika: ");
        String name = scanner.nextLine();
        System.out.println("Podaj e-mail nowego użytkownika");
        String email = scanner.nextLine();
        System.out.println("Podaj hasło nowego użytkownika");
        String password = scanner.nextLine();
        User newUser = new User(name, email, password);
        UserDao userDao = new UserDao();
        userDao.create(newUser);
        System.out.println("Użytkownik dodany pomyślnie.");
    }
    
    private static void editUser(Scanner scanner) {
        System.out.println("Podaj ID użytkownika do edycji.");
        int id = getId(scanner);
        UserDao userDao = new UserDao();
        if (userDao.read(id) == null) {
            System.out.println("Brak użytkownika o podanym ID w bazie.");
        } else {
            System.out.println("Podaj nową nazwę użytkownika: ");
            String name = scanner.nextLine();
            System.out.println("Podaj nowy e-mail użytkownika");
            String email = scanner.nextLine();
            System.out.println("Podaj nowe hasło użytkownika");
            String password = scanner.nextLine();
            User newUser = new User(name, email, password);
            newUser.setId(id);
            
            userDao.update(newUser);
            System.out.println("Edycja użytkownika zakończona pomyślnie.");
        }
    }
    
    private static void deleteUser(Scanner scanner) {
        System.out.println("Podaj ID użytkownika do usunięcia.");
        int id = getId(scanner);
        UserDao userDao = new UserDao();
        if (userDao.read(id) == null) {
            System.out.println("Brak użytkownika o podanym ID w bazie.");
        } else {
            userDao.delete(id);
            System.out.println("Użytkownik został usunięty.");
        }
    }
    
    private static void printExercises() {
        ExerciseDao exerciseDao = new ExerciseDao();
        Exercise[] exercises = exerciseDao.findAll();
        System.out.println("Lista zadań:");
        for (Exercise exercise : exercises) {
            System.out.println("ID." + exercise.getId() + " tytuł: " + exercise.getTitle());
            System.out.println("Opis: " + exercise.getDescription());
        }
    }
    
    private static void adminExercises(Scanner scanner) {
        while (true) {
            printExercises();
            System.out.println("-----------------");
            System.out.println("Wybierz jedną z opcji \nadd - dodanie zadania");
            System.out.println("edit - edycja zadania\ndelete - usunięcie zadania");
            System.out.println("quit - zakończenie programu");
            
            int option = getOption(scanner);
            switch (option) {
                case 0: {
                    System.out.println("Zakończono edycję zadań");
                    return;
                }
                case 1: {
                    addExercise(scanner);
                    break;
                }
                case 2: {
                    editExercise(scanner);
                    break;
                }
                case 3: {
                    deleteExercise(scanner);
                    break;
                }
            }
            
        }
        //powrót do początku
    }
    
    private static void addExercise(Scanner scanner) {
        System.out.println("Podaj tytuł nowego zadania:");
        String title = scanner.nextLine();
        System.out.println("Podaj opis:");
        String desc = scanner.nextLine();
        Exercise exercise = new Exercise(title, desc);
        ExerciseDao exerciseDao = new ExerciseDao();
        exerciseDao.create(exercise);
        System.out.println("Zadanie dodano pomyślnie");
    }
    
    private static void editExercise(Scanner scanner) {
        System.out.println("Podaj ID zadania do edycji.");
        int id = getId(scanner);
        ExerciseDao exerciseDao = new ExerciseDao();
        if (exerciseDao.read(id) == null) {
            System.out.println("Brak zadania o podanym ID w bazie.");
        } else {
            System.out.println("Podaj nowy tytuł dla zadania:");
            String title = scanner.nextLine();
            System.out.println("Podaj nowy opis:");
            String desc = scanner.nextLine();
            Exercise exercise = new Exercise(title, desc);
            exercise.setId(id);
            exerciseDao.update(exercise);
            System.out.println("Edycja zadania zakończona pomyślnie.");
        }
        
    }
    
    private static void deleteExercise(Scanner scanner) {
        System.out.println("Podaj ID zadania do usunięcia");
        int id = getId(scanner);
        ExerciseDao exerciseDao = new ExerciseDao();
        if (exerciseDao.read(id) == null) {
            System.out.println("Brak zadania o podanym ID w bazie.");
        } else {
            exerciseDao.delete(id);
            System.out.println("Zadanie zostało usunięte.");
        }
    }
    
    private static int getId(Scanner scanner) {
        String idStr = scanner.nextLine();
        int id = 0;
        while (!(id > 0)) {
            try {
                id = Integer.parseInt(idStr);
                if (id <= 0) {
                    System.out.println("ID nie może być mniejsze niż 0. Wpisz ponownie");
                    idStr = scanner.nextLine();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Niepoprawny argument. Wpisz ID ponownie.");
                idStr = scanner.nextLine();
            }
        }
        return id;
    }
    
    private static void adminGroups(Scanner scanner) {
        while (true) {
            GroupDao groupDao = new GroupDao();
            Group[] groups = groupDao.findAll();
            System.out.println("Lista grup:");
            for (Group group : groups) {
                System.out.println("ID." + group.getId() + " " + group.getName());
            }
            System.out.println("-----------------");
            System.out.println("Wybierz jedną z opcji \nadd - dodanie grupy");
            System.out.println("edit - edycja grupy\ndelete - usunięcie grupy");
            System.out.println("quit - zakończenie programu");
            
            int option = getOption(scanner);
            switch (option) {
                case 0: {
                    System.out.println("Zakończono edycję grup");
                    return;
                }
                case 1: {
                    addGroup(scanner);
                    break;
                }
                case 2: {
                    editGroup(scanner);
                    break;
                }
                case 3: {
                    deleteGroup(scanner);
                    break;
                }
            }
            
        }
    }
    
    private static void addGroup(Scanner scanner) {
        System.out.println("Podaj nazwę nowej grupy:");
        String name = scanner.nextLine();
        Group group = new Group(name);
        GroupDao groupDao = new GroupDao();
        groupDao.create(group);
        System.out.println("Zadanie dodano pomyślnie");
    }
    
    private static void editGroup(Scanner scanner) {
        System.out.println("Podaj ID grupy do edycji.");
        int id = getId(scanner);
        GroupDao groupDao = new GroupDao();
        if (groupDao.read(id) == null) {
            System.out.println("Brak grupy o podanym ID w bazie.");
        } else {
            System.out.println("Podaj nową nazwę dla grupy:");
            String name = scanner.nextLine();
            Group group = new Group(name);
            group.setId(id);
            groupDao.update(group);
            System.out.println("Edycja grupy zakończona pomyślnie.");
        }
    }
    
    private static void deleteGroup(Scanner scanner) {
        System.out.println("Podaj ID grupy do usunięcia");
        int id = getId(scanner);
        GroupDao groupDao = new GroupDao();
        if (groupDao.read(id) == null) {
            System.out.println("Brak grupy o podanym ID w bazie.");
        } else {
            groupDao.delete(id);
            System.out.println("Grupa została usunięta.");
        }
    }
    
    private static void adminSolutions(Scanner scanner) {
        while (true) {
            System.out.println("Wybierz jedną z opcji \nadd - przypisywanie zadań do użytkowników");
            System.out.println("view - przeglądanie rozwiązań danego użytkownika");
            System.out.println("quit - zakończenie programu");
            
            int option = getOptionForExercises(scanner);
            switch (option) {
                case 0: {
                    System.out.println("Zakończono edycję zadań");
                    return;
                }
                case 1: {
                    addExerciseToUser(scanner);
                    break;
                }
                case 2: {
                    viewSolutionsOfUser(scanner);
                    break;
                }
            }
        }
    }
    
    private static void addExerciseToUser(Scanner scanner) {

//        jeśli wybrano add – wyświetli listę wszystkich użytkowników, odpyta o id,
//        następniewyświetli listę wszystkich zadań i zapyta o id zadania,
//        utworzy i zapisze obiekt typu Solution. Pole created wypełni się automatycznie,
//        a pola updated i description mają zostaćpuste.
        printUsers();
        System.out.println();
        System.out.println("Wybierz ID użytkownika.");
        int userId = getId(scanner);
        UserDao userDao = new UserDao();
        if (userDao.read(userId) == null) {
            System.out.println("Brak użytkownika o podanym ID w bazie.");
            return;
        }
        
        printExercises();
        System.out.println("Wybierz ID zadania.");
        int exerciseId = getId(scanner);
        ExerciseDao exerciseDao = new ExerciseDao();
        if (exerciseDao.read(exerciseId) == null) {
            System.out.println("Brak zadania o podanym ID w bazie.");
            return;
        }
        Solution solution = new Solution(new Date(), exerciseId, userId);
        SolutionDao solutionDao = new SolutionDao();
        solutionDao.createPrimary(solution);
        System.out.println("Zadanie zostało przypisane.");
    }
    
    private static void viewSolutionsOfUser(Scanner scanner) {
        System.out.println("Wybierz ID użytkownika, dla którego chcesz zobaczyć rozwiązania.");
        int userId = getId(scanner);
        UserDao userDao = new UserDao();
        if (userDao.read(userId) == null) {
            System.out.println("Brak użytkownika o podanym ID w bazie.");
            return;
        }
        SolutionDao solutionDao = new SolutionDao();
        Solution[] solutions = solutionDao.findAllByUserId(userId);
        if (solutions.length == 0) {
            System.out.println("Brak rozwiązań dla wybranego użytkownika");
        } else {
            System.out.println("Lista rozwiązań:");
            for (Solution solution : solutions) {
                System.out.println("ID zadania - " + solution.getExercise_id());
                String desc = solution.getDescription();
                if (desc != null) {
                    System.out.println("Opis rozwiązania: " + desc);
                }
                Date created = solution.getCreated();
                System.out.println("Utworzone: " + solution.getCreated());
                Date updated = solution.getUpdated();
                if (updated != null) {
                    System.out.println("Zaktualizowane: " + solution.getUpdated());
                }
            }
        }
    }
    
    private static int getOptionForExercises(Scanner scanner) {
        String input = scanner.nextLine();
        while (true) {
            switch (input) {
                case "quit":
                    return 0;
                case "add":
                    return 1;
                case "view":
                    return 2;
                default: {
                    System.out.println("Nie wybrano poprawnej opcji. Wpisz ponownie");
                    input = scanner.nextLine();
                }
            }
        }
    }
}


