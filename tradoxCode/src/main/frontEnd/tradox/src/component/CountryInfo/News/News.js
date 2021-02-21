import React from 'react'
import style from './News.module.css'
export default function News(props) {
    const lastNews = Object.keys(props.data.object.news.list)
        .map(item => <span key={props.data.object.news.list[item].newsItemId}>
            {props.data.object.news.list[item].text}
            <br/></span>)
    return (
        <div className = {style.container}>
            <h2 className={style.head}>Last news</h2>
            <div className={style.innerContainer}>
                {lastNews}
            </div>
        </div>
    )
}
