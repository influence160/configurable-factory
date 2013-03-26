###configurable-factory


A generic configurable factory for creating an object. using an XML configuration to associate every Interface (or super class) to his implementation that you want to use

###How to use

create the XML config file


      <factories>
        <factory> interface="com.application.example.AInterface" implementation="com.application.example.AImpl2" />
        <factory&gt; interface="com.application.example.BInterface" implementation="com.application.example.BImpl1" />
      </factories>

<p/>
and ask the ConfigurableFactory to create an instance

      ConfigurableFactory factory = new ConfigurableFactory("the_file_path.xml");
      //this will return a new instance of AImpl2
      AInterface a = factory.createInstance(AInterface.class);
      
###Why to use

This factory can be more interesting then a simple factory class 
suppose you create a code that will be used by another developper, 
your code provides a method that makes a sertaint treatment in which 
she created the instance of a class implementing your interface IA.
<p/>
Suppose you want to leave the choice of the created instances type to the user, 
the best way is to ask her to configure this option in an xml file whose name is predefined

for example this is a framework called MyFramework, it provide this service called S

      public class S {
        private List<IA> as = new ArrayList<IA>();
        
        public void serviceMethod(Object ... args) {
            //some code
            ...
            ConfigurableFactory factory = MyFrameworkFactoryProvider.getConfigurableFactory();
            IA a = factory.createInstance(IA.class);
            as.add(a);
            //some code
            ...
        }
        
        public List<IA> getAs() {
          return this.as;
        }
      }
<p/>

      public class MyFrameworkFactoryProvider {
          //this file must be provided by the user of the framework 'MyFramework' in the classpath
          public static final String FACTORY_CONFIG_FILE = "META-INF/myframework-factories-config.xml"
          
          ConfigurableFactory factory = new ConfigurableFactory(FACTORY_CONFIG_FILE);
          
          public ConfigurableFactory getConfigurableFactory() {
            return factory;
          }
      }

and then the user can choose the implementation of IA to use that can be his own implementation

and the client code can be like this 

      myframework-factories-config.xml : 
      <factories>
        <factory> interface="com.myframework.IA" implementation="com.theclient.AClientImpl" />
      </factories>
<p/>
      // a client class
      ...
      S service = new S();
      s.serviceMethod();
      List<IA> as = s.getAs();
      AClientImpl a0 = (AClientImpl) as.get(0);
      
      
