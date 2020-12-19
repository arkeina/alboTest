<?php
/* utilizar la variable que nos viene o establecerla nosotros */
@$format = strtolower($_GET['format']) == 'json' ? 'json' : 'xml'; //xml es por defecto
$format = strtolower('json');
@$user_id = intval($_GET['user']);

/* conectamos a la bd */
$link = mysqli_connect('127.0.0.1', 'root', '') or die('No se puede conectar a la BD');

/* sacamos los posts de bd */
$query = "SELECT name, comic FROM  `marvel_repo_db`.`other_heros` WHERE id_hero_related = '1';";
$result = mysqli_query($link, $query ) or die('Query no funcional:  ' . $query);

/* creamos el array con los datos */
$posts = array();
if (mysqli_num_rows($result)) {
    while ($post = mysqli_fetch_assoc($result)) {
        $posts[] = array('post' => $post);
    }
}

/* formateamos el resultado */
if ($format == 'json') {
    header('Content-type: application/json');
    echo json_encode(array('posts' => $posts));
} else {
    header('Content-type: text/xml');
    echo '';
    foreach ($posts as $index => $post) {
        if (is_array($post)) {
            foreach ($post as $key => $value) {
                echo '<', $key, '>';
                if (is_array($value)) {
                    foreach ($value as $tag => $val) {
                        echo '<', $tag, '>', htmlentities($val), '';
                    }
                }
                echo '';
            }
        }
    }
    echo '';
}

/* nos desconectamos de la bd */
@mysqli_close($link);

?>