import java.util.Scanner;

public class UserProgram {
    
    
    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("Nie podano parametru wejściowego (ID użytkownika). Zakończenie programu.");
            return;
        }
        
        int userId;
        try{
            userId = Integer.parseInt(args[0]);
            if (userId<=0){
                System.out.println("Wprowadzono id < 0. Zakończenie programu.");
            }
        } catch (IllegalArgumentException e){
            System.out.println("Wprowadzono nieprawidłowe ID. Zakończenie programu.");
            return;
        }
    
        UserDao userDao = new UserDao();
        if (userDao.read(userId) == null) {
            System.out.println("Brak użytkownika o podanym ID w bazie. Zakończenie programu.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        
        int option = getOption(scanner);
        while (true){
            switch (option) {
                case 0:
                    return;
                case 1:
                    addSolution(scanner, userId);
                    break;
                case 2:
                    //viewSolutions();
                    break;
            }
            option = getOption(scanner);
        }
        
        

        
  
    }
    
    private static int getOption(Scanner scanner) {
        printOptions();
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
    
    private static void addSolution(Scanner scanner, int userId){
        printExercisesWithoutSolutions(findExercisesWithoutSolutions(userId));
        
        System.out.println("Wybierz zadanie, do którego chcesz dodać rozwiązanie");
        int exerciseId = getId(scanner);
        ExerciseDao exerciseDao = new ExerciseDao();
        Solution[] solutions = findExercisesWithoutSolutions(userId);
        while (exerciseDao.read(exerciseId) == null){
            System.out.println("Wybrano niepoprawne zadanie. Wpisz zadanie z listy.");
            printExercisesWithoutSolutions(solutions);
            exerciseId = getId(scanner);
        }
        Solution solutionToEdit = new Solution();
        for (Solution solution : solutions) {
            if (solution.getExercise_id() == exerciseId && solution.getUser_id() == userId) {
                solutionToEdit = solution;
                break;
            }
        }
        System.out.println("Wprowadź rozwiązanie:");
        String description = scanner.nextLine();
        solutionToEdit.setDescription(description);
        SolutionDao solutionDao = new SolutionDao();
        solutionDao.update(solutionToEdit);
        //solutionDao.
        
    
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
    
    private static void printOptions() {
        
        System.out.println("Wybierz opcję:");
        System.out.println("1 - dodawanie rozwiązań");
        System.out.println("2 - przeglądanie rozwiązań");
        System.out.println("0 - zakończ program");
    }
    
    private static void printExercisesWithoutSolutions(Solution[] solutions) {
        if (solutions == null){
            System.out.println("Brak zadań do rozwiązania.");
            return;
        }
        ExerciseDao exerciseDao = new ExerciseDao();
        System.out.println("Zadania do rozwiązania:");
        for (Solution solution : solutions){
            System.out.print("ID." + exerciseDao.read(solution.getExercise_id()).getId());
            System.out.println(" Tytuł: " + exerciseDao.read(solution.getExercise_id()).getTitle());
            System.out.println("Opis: " + exerciseDao.read(solution.getExercise_id()).getDescription());
        }
    }
    
    private static Solution[] findExercisesWithoutSolutions(int user_id) {
        SolutionDao solutionDao = new SolutionDao();
        Solution[] solutions = solutionDao.findAllByUserIdWithoutSolution(user_id);
        if (solutions == null){
            System.out.println("Brak zadań do rozwiązania.");
            return null;
        }
        ExerciseDao exerciseDao = new ExerciseDao();
        return solutions;
    }
}
