package controlador;

import model.BooksEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class controladorLibro {
    private BooksEntity booksEntity;
    private int opcion;
    private Scanner scanner;
    private Session session;

    public controladorLibro(Session session){
        this.session = session;
        this.scanner = new Scanner(System.in);
        this.opcion = 0;

        menu(opcion);
    }

    public void crearLibros() {
        this.booksEntity = new BooksEntity();
        this.booksEntity.setId(null);
        System.out.println("Escriba el autor del libro:");
        scanner.nextLine();
        this.booksEntity.setAuthor(scanner.nextLine());

        System.out.println("Escriba el titulo del libro:");
        this.booksEntity.setTitle(scanner.nextLine());

        System.out.println("Escriba la categoria del libro:");
        this.booksEntity.setCategory(scanner.nextLine());

        System.out.println("Escriba la descripcion del libro:");
        this.booksEntity.setDescri(scanner.nextLine());


        System.out.println(this.booksEntity);

        session.getTransaction().begin();
        this.session.persist(this.booksEntity);
        session.getTransaction().commit();

        System.out.println("Libro registrado \n");


    }

    public void buscarLibro() {
        this.booksEntity = new BooksEntity();
        System.out.println("Id:");
        int idLibro = scanner.nextInt();

        Query query =session.createQuery("Select p FROM BooksEntity p where p.id = "+idLibro);
        List<BooksEntity> listLibros = query.list();
        int contador = -1;
        for(BooksEntity libros: listLibros){
            System.out.println("Id: "+ libros.getId());
            System.out.println("Autor: "+libros.getAuthor());
            System.out.println("Titulo: "+libros.getTitle());
            System.out.println("Category: "+libros.getCategory());
            System.out.println("Descripción: "+libros.getDescri());
            contador++;
        }
        if (contador==-1)
            System.out.println("Libro no encontrada \n");
    }

    public void actualizarLibro() {
        this.booksEntity = new BooksEntity();
        System.out.println("Id:");
        int idLibro = scanner.nextInt();
        //int opcion2=0;

        Query query =session.createQuery("Select p FROM BooksEntity p where p.id = "+idLibro);
        List<BooksEntity> listPiezas = query.list();
        int contador = -1;
        for(BooksEntity libros: listPiezas){
            this.booksEntity.setId(libros.getId());
            contador++;
        }
        if(contador!=-1){

            System.out.println("Escriba el autor del libro:");
            scanner.nextLine();
            this.booksEntity.setAuthor(scanner.nextLine());

            System.out.println("Escriba el titulo del libro:");
            this.booksEntity.setTitle(scanner.nextLine());

            System.out.println("Escriba la categoria del libro:");
            this.booksEntity.setCategory(scanner.nextLine());

            System.out.println("Escriba la descripcion del libro:");
            this.booksEntity.setDescri(scanner.nextLine());


            session.getTransaction().begin();
            session.merge(this.booksEntity);
            session.getTransaction().commit();
            System.out.println("Libro actualizada \n");

            //Para editar solamente el seleccionado (Más adelante, no funciona)
            /* while (opcion2!=5) {
                System.out.println("1. Autor");
                System.out.println("2. Titulo");
                System.out.println("3. Categoria");
                System.out.println("4. Descripción");
                System.out.println("5. Salir");
                System.out.println("Elija una opción:");

                opcion2 = scanner.nextInt();
                switch (opcion2) {
                    case 1:
                    //Si no funciona así, hacerlo con query
                        System.out.println("Escriba el autor del libro:");
                        scanner.nextLine();
                        this.booksEntity.setAuthor(scanner.nextLine());
                        session.getTransaction().begin();
                        session.merge(this.booksEntity);
                        session.getTransaction().commit();
                        System.out.println("Libro actualizada \n");
                        break;

                    case 2:
                        System.out.println("Escriba el titulo del libro:");
                        scanner.nextLine();
                        this.booksEntity.setTitle(scanner.nextLine());
                        session.getTransaction().begin();
                        session.merge(this.booksEntity);
                        session.getTransaction().commit();
                        System.out.println("Libro actualizada \n");
                        break;
                    case 3:
                        System.out.println("Escriba la categoria del libro:");
                        scanner.nextLine();
                        this.booksEntity.setCategory(scanner.nextLine());
                        session.getTransaction().begin();
                        session.merge(this.booksEntity);
                        session.getTransaction().commit();
                        System.out.println("Libro actualizada \n");
                        break;
                    case 4:
                        System.out.println("Escriba la descripcion del libro:");
                        scanner.nextLine();
                        this.booksEntity.setDescri(scanner.nextLine());
                        session.getTransaction().begin();
                        session.merge(this.booksEntity);
                        session.getTransaction().commit();
                        System.out.println("Libro actualizada \n");
                        break;
                    case 5:
                        session.close();

                        break;

                    default:
                        System.out.println("Opción no válida\n");
                        break;
                }
            }*/
        }else
            System.out.println("Libro no encontrada \n");
    }

    public void borrarLibros() {
        int contador=-1;
        this.booksEntity = new BooksEntity();
        System.out.println("Id:");
        int idLibro = scanner.nextInt();

        Query query =session.createQuery("Select p FROM BooksEntity p where p.id = "+idLibro);
        List<BooksEntity> listLibros = query.list();
        for (BooksEntity libros : listLibros) {
            session.getTransaction().begin();
            session.delete(libros);
            session.getTransaction().commit();
            System.out.println("Libros eliminada \n");
            contador++;
        }

        if (contador == -1)
            System.out.println("Libros no encontrada \n");

    }
    public void menu(int opcion) {
        while (opcion!=5) {
            System.out.println("1. Crear Libros");
            System.out.println("2. Buscar Libros");
            System.out.println("3. Actualizar Libros");
            System.out.println("4. Eliminar Libros");
            System.out.println("5. Salir");
            System.out.println("Elija una opción:");

            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    crearLibros();
                    break;

                case 2:
                    buscarLibro();
                    break;
                case 3:
                    actualizarLibro();
                    break;
                case 4:
                    borrarLibros();
                    break;
                case 5:
                    session.close();

                    break;

                default:
                    System.out.println("Opción no válida\n");
                    break;
            }
        }
    }
}
