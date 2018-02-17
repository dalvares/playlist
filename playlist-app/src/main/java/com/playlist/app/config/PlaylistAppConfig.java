package com.playlist.app.config;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.playlist.api.config.PlaylistApiConfig;
import com.playlist.manager.config.PlaylistManagerConfig;

@Configuration
@ComponentScan("com.playlist.app")
@Import({PlaylistManagerConfig.class,PlaylistApiConfig.class})
public class PlaylistAppConfig {

}
