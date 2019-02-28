import React, { Component } from 'react';
import styles from './toast.module.scss';
import sts from './transition.module.scss';

export default class Notice extends Component {
  constructor(props) {
    super(props);
    this.state = {
      icons: {
        info: '',
        warn: sts['icon-jinggao'],
        error: sts['icon-cuowu'],
        success: sts['icon-chenggong']
      }
    };
  }
  render() {
    return (
      <div className={`${styles.notice} ${styles[this.props.type]}`}>
        <i className={`${sts.iconfont} ${this.state.icons[this.props.type]}`} />
        {this.props.message}
      </div>
    );
  }
}
