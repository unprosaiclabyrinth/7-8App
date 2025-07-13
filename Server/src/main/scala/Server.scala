object Server:
  private final val PORT: Int = 5555
  
  @main
  def runServer(): Unit = ServerEngine.start(PORT)
