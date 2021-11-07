package helpers.factories.people;

public enum SingletonPeopleFactory {
  INSTANCE;

  public ActorFactory getActorFactory() {
    return new ActorFactory();
  }

  public DirectorFactory getDirectorFactory() {
    return new DirectorFactory();
  }
}
