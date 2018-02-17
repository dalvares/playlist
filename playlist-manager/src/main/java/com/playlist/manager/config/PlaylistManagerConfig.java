package com.playlist.manager.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.playlist.db.config.PlaylistDbConfig;

@Configuration
@ComponentScan("com.playlist.manager")
@Import({PlaylistDbConfig.class})
public class PlaylistManagerConfig {

}
