package de.metamob.jndi;

import org.wicketstuff.javaee.naming.IJndiNamingStrategy;

public class Jboss7JndiNamingStrategy implements IJndiNamingStrategy {

    private static final long serialVersionUID = -6214488636329499548L;

    private final String baseName;

    public Jboss7JndiNamingStrategy() {
              this.baseName = (new StringBuilder()).append("java:module/").toString();
    }

    public Jboss7JndiNamingStrategy(final String moduleName) {
              this.baseName = (new StringBuilder()).append("java:app/").append(moduleName)
                                  .append("/").toString();
    }

    public Jboss7JndiNamingStrategy(final String applicationName, final String moduleName) {
              if ((applicationName == null) || applicationName.isEmpty()) {
                        this.baseName = (new StringBuilder()).append("java:global/")
                                            .append(moduleName).append("/").toString();
              } else {
                        this.baseName = (new StringBuilder()).append("java:global/")
                                            .append(applicationName).append("/").append(moduleName)
                                            .append("/").toString();
              }
    }

    @Override
    public String calculateName(final String ejbName, final Class<?> ejbType) {
              return new StringBuilder().append(this.baseName).append(ejbName).append("!")
                                  .append(ejbType.getCanonicalName()).toString();
    }
}