package injection;

// TODO Question 5 : l'annotation doit être lisible à l'exécution et se poser sur des champs
public @interface Inject {

    Class<?> value();

}
